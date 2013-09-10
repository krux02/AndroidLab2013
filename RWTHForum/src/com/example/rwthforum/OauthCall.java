package com.example.rwthforum;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class OauthCall extends AsyncTask<Void, Void, String> {
	private static final String TAG = "OauthCall";
	private ProgressDialog progDialog;
	private String oAuth_url, client_id, scope;
	private WaitForVerificationAct activity;

	public OauthCall(WaitForVerificationAct activity, String oAuth_url,
			String client_id, String scope) {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "In task's constructor");
		this.activity = activity;
		this.oAuth_url = oAuth_url;
		this.client_id = client_id;
		this.scope = scope;
		Log.d(TAG, "Done in task's constructor");
	}

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Processing",
				"Connecting the server", true, false);
		Log.d(TAG, "onPreExecute");

	}

	@Override
	protected String doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		String result = "";
		Log.d(TAG, "doInBackground");
		// TODO Auto-generated method stub
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(oAuth_url);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
			nvp.add(new BasicNameValuePair("client_id", client_id));
			nvp.add(new BasicNameValuePair("scope", scope));
			post.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse response = client.execute(post);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != 200) {
				Log.d(TAG, "oops");
			}
			HttpEntity entity = response.getEntity();
			InputStream ist = entity.getContent();
			ByteArrayOutputStream content = new ByteArrayOutputStream();
			int count = 0;
			byte[] buff = new byte[1024];
			while ((count = ist.read(buff)) != -1)
				content.write(buff, 0, count);
			result = new String(content.toByteArray());
			Log.d(TAG, "value= " + result);
			// JSONObject result=new JSONObject(k);
			// String device_token=result.getString("device_code");
			// Log.d(TAG,"device_code= "+device_token);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return result;
	}

	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progDialog.dismiss();
		Log.d(TAG, "onPostExecute");
		JSONObject reply;
		try {
			reply = new JSONObject(result);

			String device_code = reply.getString("device_code");
			String user_code = reply.getString("user_code");
			String ver_url = reply.getString("verification_url");
			String expires_in = reply.getString("expires_in");
			String interval = reply.getString("interval");
			this.activity.setTextViews(device_code, user_code, ver_url,
					expires_in, interval);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
