package com.example.demooauthtesting;

import java.net.MalformedURLException;
import java.net.URL;

public class OAuth {

	public void testOAuthURL() {
		HttpTask task = new HttpTask(this);
		try {
			String url = "https://service.campus.rwth-aachen.de/oauth2/oauth2.svc/code";
			task.execute(new URL(url));
			//System.out.println(response);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void methodRedirect(String result) {
		System.out.println(result);
	}
}
