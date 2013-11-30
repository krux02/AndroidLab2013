package com.example.l2plabv0;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class CommonData {

	public enum TypeOfHttpOperation {
		OAUTH_CALL, OAUTH_POLL, OAUTH_APICALL_POSSIBLE, OAUTH_NONE
	};

	public static TypeOfHttpOperation currentOperation = TypeOfHttpOperation.OAUTH_NONE;

	public static String GetOAuthLoginUrl() {
		//String url = "http://seoul.freehostia.com/oAuth_poll.php";
		String url = "https://oauth.campus.rwth-aachen.de/oauth2waitress/oauth2.svc/code";
		return url;
	}

	public static String GetManageDeviceCodeUrl() {
		String url = "https://oauth.campus.rwth-aachen.de/manage/?q=verify&d=";
		return url;
	}

	public static String GetOAuthPollUrl() {
		String url = "https://oauth.campus.rwth-aachen.de/oauth2waitress/oauth2.svc/token";
		return url;
	}

	public static void writeLog(String subsection, String msg) {
		String base = "# ";
		Log.d(base + subsection, msg);
	}

	public static void writeLog(String subsection, Exception e) {
		String base = "# ";
		Log.e(base + subsection, e.getClass().getName() + ": " + e.getMessage());
	}
	
	public static String getSingleValueFromKey(String key, Context context) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		long systemTime = (System.currentTimeMillis() / 1000L);
		long sharedprefTime = settings.getLong("EXPIRES_IN", -1);
		if (sharedprefTime < systemTime) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(key, "").commit();
		}
		return settings.getString(key, "");
	}


	public static void printAllSharedPreference(Context context) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		Map<String, ?> keys = settings.getAll();

		for (Map.Entry<String, ?> entry : keys.entrySet()) {
			CommonData.writeLog("Map values", entry.getKey() + ": "
					+ entry.getValue().toString());
		}
	}

	public static void saveAllCodesIntoSharedPreference(String json,
			Context context) throws Exception {
		try {
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = settings.edit();
			JSONObject jsonObject = new JSONObject(json);

			String status = jsonObject.getString("status");
			if(status.equalsIgnoreCase("error: device code invalid.")){
				CommonData.deleteSingleSharedPreference("DEVICE_CODE", context);
				Exception e = new Exception("Deleted invalid device code");
				CommonData.writeLog("410", e);
				throw e;
			}
			if (status.equalsIgnoreCase("ok") == false) {
				CommonData.writeLog("onGlobalVariables Error CALL",
						jsonObject.getString("status"));
				return;
			}

			Long expireTime = (long) (System.currentTimeMillis() / 1000L)
					+ jsonObject.getLong("expires_in");
			editor.putLong("EXPIRES_IN", expireTime);

			if (CommonData.currentOperation == TypeOfHttpOperation.OAUTH_POLL) {
				String accessToken = jsonObject.getString("access_token");
				editor.putString("ACCESS_TOKEN", accessToken);
			} else if (CommonData.currentOperation == TypeOfHttpOperation.OAUTH_CALL) {
				// String refreshCode = jsonObject.getString("refresh_token");
				String deviceCode = jsonObject.getString("device_code");
				String userCode = jsonObject.getString("user_code");
				editor.putString("DEVICE_CODE", deviceCode);
				editor.putString("USER_CODE", userCode);
			}
			editor.commit();
		} catch (Exception e) {
			//CommonData.writeLog("JSON result", e);
			throw e;
		}

	}

	public static void deleteSingleSharedPreference(String key, Context context) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putString(key, "").commit();
	}
	
	public static void deleteAllSharedPreferences(Context context) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		// settings.edit().remove("expires_in").commit();
		// settings.edit().remove("OAUTH_CALL_URL").commit();
		Map<String, ?> keys = settings.getAll();

		for (Map.Entry<String, ?> entry : keys.entrySet()) {
			CommonData.writeLog("Deleting map value", entry.getKey() + ": "
					+ entry.getValue().toString());
			settings.edit().remove(entry.getKey()).commit();
		}
	}

	public static String POST(String oAuthURL, String oAuthParam)
			throws Exception {
		String jsonData = "";
		try {
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

			BufferedReader reader = null;
			String fetchedData = "";
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				// System.out.println(line);
				fetchedData += line;
			}
			jsonData = fetchedData;
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			throw e;
		}

		return jsonData;
	}

}
