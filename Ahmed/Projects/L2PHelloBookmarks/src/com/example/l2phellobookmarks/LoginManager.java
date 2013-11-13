package com.example.l2phellobookmarks;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

public class LoginManager extends AsyncTask<String, Integer, String> {
	private Context context;
	LoginActivityUI loginUI;
	private Boolean dataDownloadComplete;

	public enum TypeOfHttpOperation {
		OAUTH_CALL, OAUTH_POLL, OAUTH_USER_AUTHORIZE_PENDING, OAUTH_APICALL_POSSIBLE, OAUTH_REFRESH_NECESSARY
	};

	TypeOfHttpOperation operationType;

	public LoginManager(Context parent, LoginActivityUI oLoginUI) {
		this.context = parent;
		this.loginUI = oLoginUI;
		this.operationType = TypeOfHttpOperation.OAUTH_CALL;
		this.dataDownloadComplete = false;
	}

	private String updateNextOperationType() {
		String title = "";
		long currentTime = (long) (System.currentTimeMillis() / 1000L);
		if (GlobalVariables.hasRefreshCode == true) {
			// one condition missed = check if the code is still valid
			this.operationType = TypeOfHttpOperation.OAUTH_APICALL_POSSIBLE;
			title = "Success! You can continue...";
		} else if (GlobalVariables.hasDeviceCode
				&& GlobalVariables.tokenExpireAtUnixTimestamp >= currentTime) {
			this.operationType = TypeOfHttpOperation.OAUTH_USER_AUTHORIZE_PENDING;
			title = "Please authorize the app.";
		} else {
			this.operationType = TypeOfHttpOperation.OAUTH_CALL;
			title = "App authorization required!";
		}
		return title;
	}

	protected void onPreExecute() {
		Log.d("PreExceute", "On pre Exceute......");

		GlobalVariables.populateCodesFromSharedPreference(this.context);
		String title = this.updateNextOperationType();
		new AlertDialog.Builder(this.context).setTitle(title)
				.setMessage("Click ok to generate communicating URL")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// continue with delete
						;
					}
				}).show();
	}

	private String readStream(InputStream istream) {
		String fetchedData = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(istream));
			String line = "";
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				fetchedData += line;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fetchedData;
	}

	protected String doInBackground(String... arg0) {

		String jsonData = "";
		String oAuthURL = "";
		String oAuthParam = "";

		while (this.dataDownloadComplete == false) {
			try {
				GlobalVariables.populateCodesFromSharedPreference(this.context);
				this.updateNextOperationType();
				switch (this.operationType) {
				case OAUTH_CALL:
					oAuthURL = GlobalVariables.GetOAuthLoginUrl();
					oAuthParam = "scope=l2p.rwth&client_id="
							+ this.context
									.getString(R.string.APPLICATION_SECRET_KEY);
					break;
				case OAUTH_USER_AUTHORIZE_PENDING:
					// will call progress update here
				case OAUTH_POLL:
					oAuthURL = GlobalVariables.GetOAuthPollUrl();
					oAuthParam = "grant_type=device&client_id="
							+ this.context
									.getString(R.string.APPLICATION_SECRET_KEY)
							+ "&code=" + GlobalVariables.device_code;
					break;

				case OAUTH_REFRESH_NECESSARY:
					break;
				case OAUTH_APICALL_POSSIBLE:
					this.dataDownloadComplete = true;
					continue;
				default:
					break;
				}

				URL url = new URL(oAuthURL);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setInstanceFollowRedirects(false);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				connection.setRequestProperty("charset", "utf-8");
				connection.setRequestProperty("Content-Length",
						"" + Integer.toString(oAuthParam.getBytes().length));
				connection.setUseCaches(false);
				DataOutputStream wr = new DataOutputStream(
						connection.getOutputStream());
				wr.writeBytes(oAuthParam);
				wr.flush();
				wr.close();

				jsonData = this.readStream(connection.getInputStream());
				connection.disconnect();

				this.updateOAuthServerResponseData(jsonData, this.context);
				// Implement to increase attampt count;
			} catch (Exception e) {
				Log.d("doInBackground Error", e.getMessage());
			}
		}

		// jsonData =
		// "{'status':'ok','device_code':'ufojODGUsFxir3uF8lca7GDtpEfeOTki','expires_in':1800,'interval':5,'user_code':'MXLPEWAYNL','verification_url':'https://service.campus.rwth-aachen.de/oauth2/oauth2.svc/manage'}";
		return jsonData;
	}

	protected void onProgressUpdate(Integer... a) {
		Log.d("onProgressUpdate", "Progress Update.........." + a[0].toString());
	}

	protected void onPostExecute(String result) {
		Log.d("onPostExecute", result);
		this.updateOAuthServerResponseData(result, this.context);
	}

	private void updateOAuthServerResponseData(String json, Context context) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			String status = jsonObject.getString("status");
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = settings.edit();

			if (status.equalsIgnoreCase("ok") == false
					&& this.operationType == TypeOfHttpOperation.OAUTH_CALL) {
				Log.d("onGlobalVariables Error CALL",
						jsonObject.getString("status"));
				return;
			} else if (status.equalsIgnoreCase("ok") == false
					&& this.operationType == TypeOfHttpOperation.OAUTH_POLL) {
				Log.d("onGlobalVariables Error POLL",
						jsonObject.getString("status"));
				return;
			} else if (status.equalsIgnoreCase("error: authorization pending") == true
					&& this.operationType == TypeOfHttpOperation.OAUTH_USER_AUTHORIZE_PENDING) {
				//it's ok to wait and do nothing in this state, user has to authorize the app.
				return;
			}

			if (this.operationType == TypeOfHttpOperation.OAUTH_POLL
					|| this.operationType == TypeOfHttpOperation.OAUTH_USER_AUTHORIZE_PENDING) {
				String accessCode = jsonObject.getString("access_token");
				String refreshCode = jsonObject.getString("refresh_token");
				Long expireTime = (long) (System.currentTimeMillis() / 1000L)
						+ jsonObject.getLong("expires_in");
				
				if (accessCode.equalsIgnoreCase("")) {
					Log.d("onGlobalVariables Error Final token",
							"No valid token found");
					return;
				}
				editor.putLong("EXPIRES_IN", expireTime);
				editor.putString("REFRESH_CODE", refreshCode);
				editor.putString("FINAL_TOKEN", accessCode);
				
				GlobalVariables.apiCallToken = accessCode;
				GlobalVariables.tokenExpireAtUnixTimestamp = expireTime;

			} else if (this.operationType == TypeOfHttpOperation.OAUTH_CALL) {
				Long expireTime = (long) (System.currentTimeMillis() / 1000L)
						+ jsonObject.getLong("expires_in");

				editor.putString("DEVICE_CODE",
						jsonObject.getString("device_code"));
				editor.putLong("EXPIRES_IN", expireTime);
				editor.putString("USER_CODE", jsonObject.getString("user_code"));

				GlobalVariables.device_code = jsonObject
						.getString("device_code");
				GlobalVariables.user_code = jsonObject.getString("user_code");
			}
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}