package com.example.l2plabv0;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class CommonHttpGETorPOST extends AsyncTask<String, String, String>{

    protected String doInBackground(String... urls) {
    	String jsonData = "";
        try {
        	String givenUrl = urls[0];
        	HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(givenUrl);
			HttpResponse responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();
			if (resEntityGet != null) {
				// do something with the response
				jsonData = EntityUtils.toString(resEntityGet);
				Log.i("GET RESPONSE", jsonData);
			}
        } catch (Exception e) {
            CommonData.writeLog("HTTP GET", e);
        }
        return jsonData;
    }

    protected void onPostExecute(String returnData) {
    }
}
