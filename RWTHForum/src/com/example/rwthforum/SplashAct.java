package com.example.rwthforum;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class SplashAct extends Activity {

	private static final String TAG = "SplashAtivity";
	public static SharedPreferences sp;
	private SharedPreferences.Editor edtr;
	private int flag;
	private String client_id, oAuth_url, scope, device_code, user_code,
			verification_url, url_expires_in, interval, poll_url, access_token,
			token_type, token_expires_in, refresh_token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "In OnCreate method");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_splash);
		initialize();
		authentication();

	}

	private void initialize() {
		// TODO Auto-generated method stub
		flag = 0;
		Log.d(TAG, "initialize");
		sp = getSharedPreferences("RWTH_File", 0);
		edtr = sp.edit();
		if (sp.getString("user_code", null) == null) {
			edtr.putString(
					"client_id",
					"Se8Uit5Hd244cltskaVUpHFVXaZ6exvKVTKsKhKTH70yTlPlWoHHUR5RFsy30nV.app.rwth-aachen.de");
			edtr.putString("oAuth_url",
					"https://service.campus.rwth-aachen.de/oauth2/oauth2.svc/code");
			edtr.putString("poll_url",
					"https://service.campus.rwth-aachen.de/oauth2/oauth2.svc/token");
			edtr.putString("scope", "l2p.rwth");
			edtr.putString("grant_type", "device");
			edtr.putString("device_code", null);
			edtr.putString("user_code", null);
			edtr.putString("verification_url", null);
			edtr.putString("url_expires_in", null);
			edtr.putString("interval", null);
			edtr.putString("access_token", null);
			edtr.putString("token_type", null);
			edtr.putString("token_expires_in", null);
			edtr.putString("refresh_token", null);
			edtr.commit();
		}
		access_token = sp.getString("access_token", null);
		user_code = sp.getString("user_code", null);
	
		
	}

	private void authentication() {
		// TODO Auto-generated method stub
		Log.d(TAG, "authentication");
		String access_token = sp.getString("access_token", null);
		String user_code = sp.getString("user_code", null);
		
			if (access_token != null) {
				flag = 1;
				timer();
			}
			if (access_token == null && user_code != null) {
				flag = 2;
				timer();
			}
			if (user_code == null) {
				flag = 3;
				timer();
			}
		

	}

	private void timer() {
		// TODO Auto-generated method stub
		Log.d(TAG, "In timer method");
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					switch (flag) {
					case 1:
						Log.d(TAG, "1");
						Intent intent = new Intent(SplashAct.this,
								WelcomeAct.class);
						startActivity(intent);
						finish();						
						break;
					case 2:
						Log.d(TAG, "2");
						Intent i3 = new Intent(SplashAct.this,
								WaitForVerificationAct.class);
						startActivity(i3);
						finish();
						break;
					case 3:
						Log.d(TAG, "3");
						Intent k3 = new Intent(SplashAct.this,
								WaitForVerificationAct.class);
						startActivity(k3);
						finish();
						break;
					
					}
				}
			}
		};
		Log.d(TAG, "Starting Thread");
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.d(TAG, "In OnPause method");
		super.onPause();
		finish();
	}

	

}
