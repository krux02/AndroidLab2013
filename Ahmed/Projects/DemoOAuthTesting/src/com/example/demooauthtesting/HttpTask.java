package com.example.demooauthtesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.os.AsyncTask;
import android.util.Log;

public class HttpTask extends AsyncTask<URL, Integer, String> {

	private OAuth OAuthActivity;
	public enum uRLCallType { OAUTH_REQUEST, OAUTH_PULL, CALL_URL };
	
	public HttpTask(OAuth m) {
		this.OAuthActivity = m;
	}
	
	private HttpClient createHttpClient()
	{
	    HttpParams params = new BasicHttpParams();
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
	    HttpProtocolParams.setUseExpectContinue(params, true);

	    SchemeRegistry schReg = new SchemeRegistry();
	    schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	    schReg.register(new Scheme("https", (SocketFactory) SSLSocketFactory.getDefault(), 443));
	    ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

	    return new DefaultHttpClient(conMgr, params);
	}

	@Override
	protected String doInBackground(URL... params) {
		// TODO Auto-generated method stub
		HttpClient httpclient = this.createHttpClient(); // new DefaultHttpClient();
		String result = "";
		// Prepare a request object
		String url = params[0].toString();
		
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs
				.add(new BasicNameValuePair(
						"client_id",
						"EdtXGQg4npM5FJtG3eEQd0ofTEOTGdDuN9AehN2LcrUJndiBRRbH7e4JGSFXZXfR.apps.rwth-aachen.de"));
		nameValuePairs.add(new BasicNameValuePair("scope", "l2p.rwth"));

		// Execute the request
		HttpResponse response;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = httpclient.execute(httpPost);
			// Examine the response status
			Log.i("Response status: ", response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				result += convertStreamToString(instream);
				Log.i("Downloaded result: ", result);
				// Closing the input stream will trigger connection release
				instream.close();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	private String convertStreamToString(InputStream is) {
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	protected void onPostExecute(String result) {
		// Log.d("Downloaded data", result);
		this.OAuthActivity.methodRedirect(result);
	}
}
