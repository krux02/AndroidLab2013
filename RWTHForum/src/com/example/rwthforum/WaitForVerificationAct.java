package com.example.rwthforum;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WaitForVerificationAct extends Activity implements OnClickListener {
	private static final String TAG = "Wait4VerificationAct";
	public static SharedPreferences sp;
	private SharedPreferences.Editor edtr;
	private TextView tUser_code, tVerification_url;
	private Button bVerify;
	private String oAuth_url, client_id, scope, grant_type;
	private static long i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_wait_for_verification);
		Log.d(TAG, "In OnCreate method");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initialize();
		action();

	}

	private void initialize() {
		// TODO Auto-generated method stub
		Log.d(TAG, "initialize");
		tUser_code = (TextView) findViewById(R.id.tUser_code);
		tVerification_url = (TextView) findViewById(R.id.tVerification_url);
		bVerify = (Button) findViewById(R.id.bVerify);
		sp = getSharedPreferences("RWTH_File", 0);
		edtr = sp.edit();
		oAuth_url = sp.getString("oAuth_url", null);
		client_id = sp.getString("client_id", null);
		scope = sp.getString("scope", null);
		grant_type = sp.getString("grant_type", null);
	}

	private void action() {
		// TODO Auto-generated method stub
		Log.d(TAG, "In action");
		String user_code = sp.getString("user_code", null);
		if (user_code != null) {
			tUser_code.setText(user_code);
			tVerification_url.setText(sp.getString("verification_url", null));
			bVerify.setOnClickListener(this);
			String m = sp.getString("interval", null);
			i = Integer.parseInt(m) * 1000 + 1;
			bVerify.setOnClickListener(this);
			polls();
		} else {
			Log.d(TAG, "calling OauthCall");
			OauthCall call = new OauthCall(this, oAuth_url, client_id, scope);
			call.execute();
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		// fire intent to start browser
		String user_code = sp.getString("user_code", null);
		String verification_url = sp.getString("verification_url", null);
		String url = verification_url + "?q=verify&d=" + user_code;
		Log.d(TAG, "user sent to: " + url);
		Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(myIntent);
		polls();
	}

	public void setTextViews(String device_code, String user_code,
			String ver_url, String expires_in, String interval) {
		// TODO Auto-generated method stub
		Log.d(TAG, "In setTextViews");
		Log.d(TAG, "vals= " + device_code + " " + ver_url);
		while (user_code == null) {
			OauthCall call = new OauthCall(this, oAuth_url, client_id, scope);
			call.execute();
		}
		tUser_code.setText(user_code);
		tVerification_url.setText(ver_url);
		edtr.putString("device_code", device_code);
		edtr.putString("user_code", user_code);
		edtr.putString("verification_url", ver_url);
		edtr.putString("url_expires_in", expires_in);
		edtr.putString("interval", interval);
		edtr.commit();
		i = (long) Integer.parseInt(interval) * 1000 + 1;
		bVerify.setOnClickListener(this);
		polls();
	}

	private void polls() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("RWTH_File", 0);
		String poll_url = sp.getString("poll_url", null);
		String device_code = sp.getString("device_code", null);
		OauthPoll poll = new OauthPoll(this, client_id, device_code,
				grant_type, poll_url);
		poll.execute();
	}

	public Boolean pollResult(String status, String access_token,
			String token_type, String expires_in, String refresh_token) {
		// TODO Auto-generated method stub
		if (status.equals("error: authorization pending.")) {
			Toast.makeText(this, R.string.auth_pending, Toast.LENGTH_LONG)
					.show();
			timer();

			return true;
		}
		if (status.equals("error: slow down.")) {
			Toast.makeText(this, R.string.auth_pending, Toast.LENGTH_LONG)
					.show();
			i = i + 200l;
			timer();

			return true;
		}
		if (status.equals("ok")) {
			edtr.putString("access_token", access_token);
			edtr.putString("token_type", token_type);
			edtr.putString("token_expires_in", expires_in);
			edtr.putString("refresh_token", refresh_token);
			edtr.commit();
			Intent intent = new Intent(this, WelcomeAct.class);
			startActivity(intent);
			finish();
		}
		return false;

	}

	private void timer() {
		// TODO Auto-generated method stub
		Log.d(TAG, "In timer method");
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					polls();
				}
			}
		};
		Log.d(TAG, "Starting Thread");
		timer.setDaemon(true);
		timer.start();
	}

}
