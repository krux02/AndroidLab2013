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

public class OauthPoll extends AsyncTask<Void, Void, String> {
	private static final String TAG = "OauthPoll";	
	private String poll_url, client_id, grant_type,device_code;
	private WaitForVerificationAct activity;

	

	public OauthPoll(WaitForVerificationAct activity, String client_id,
			String device_code, String grant_type, String poll_url) {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "In task's constructor");
		this.activity = activity;
		this.poll_url = poll_url;
		this.client_id = client_id;
		this.device_code=device_code;
		this.grant_type = grant_type;
		Log.d(TAG, "Done in task's constructor");
	}

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
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
			HttpPost post = new HttpPost(poll_url);
			List<NameValuePair> nvp = new ArrayList<NameValuePair>(3);
			nvp.add(new BasicNameValuePair("client_id", client_id));
			nvp.add(new BasicNameValuePair("code", device_code));
			nvp.add(new BasicNameValuePair("grant_type", grant_type));
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
		Log.d(TAG, "onPostExecute");
		JSONObject reply;
		try {
			reply = new JSONObject(result);

			String status = reply.getString("status");
			String access_token = reply.getString("access_token");
			String token_type = reply.getString("token_type");
			String expires_in = reply.getString("expires_in");
			String refresh_token = reply.getString("refresh_token");
			this.activity.pollResult(status, access_token,token_type ,
					expires_in, refresh_token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
