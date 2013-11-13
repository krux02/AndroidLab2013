package com.example.demooauthtesting;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class OauthCall extends AsyncTask<Void, Void, String> {

	private String TAG = "Inside OAuthCall";
	@Override
	protected String doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		Log.i(TAG, "onPreExecute");
	}

	protected String doInBackground1(Void... arg0) {
		// TODO Auto-generated method stub
		String result = "";
		Log.d(TAG, "doInBackground");
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("https://service.campus.rwth-aachen.de/oauth2/oauth2.svc/code");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("client_id", "EdtXGQg4npM5FJtG3eEQd0ofTEOTGdDuN9AehN2LcrUJndiBRRbH7e4JGSFXZXfR.apps.rwth-aachen.de"));
	        nameValuePairs.add(new BasicNameValuePair("scope", "l2p.rwth"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }

		return result;
	}

	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
}
