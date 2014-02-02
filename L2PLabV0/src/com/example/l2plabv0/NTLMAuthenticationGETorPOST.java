package com.example.l2plabv0;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class NTLMAuthenticationGETorPOST extends AsyncTask<String, String, String>{

    protected String doInBackground(String... urls) {
    	String jsonData = "";
        try {
        	String givenUrl = urls[0];
        	
        	DefaultHttpClient client = new DefaultHttpClient();
        	client.getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
            client.getCredentialsProvider().setCredentials(
                    // Limit the credentials only to the specified domain and port
                    new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    // Specify credentials, most of the time only user/pass is needed
                    new NTCredentials("arnab", "Dev#123", "", "")
            );
			HttpGet get = new HttpGet(givenUrl);
			HttpResponse responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();
			if (resEntityGet != null) {
				// do something with the response
				jsonData = EntityUtils.toString(resEntityGet);
			}
        } catch (Exception e) {
            CommonData.writeLog("HTTP GET", e);
        }
        return jsonData;
    }
}
