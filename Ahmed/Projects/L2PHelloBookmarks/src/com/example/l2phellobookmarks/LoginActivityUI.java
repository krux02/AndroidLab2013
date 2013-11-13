package com.example.l2phellobookmarks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class LoginActivityUI extends Activity {
	public static String OAUTH_CALL_URL = "http://example.com";
	public static String OAUTH_POLL_URL = "";
	public static String OAUTH_REFRESH_TOKEN = "";
	public static String SCOPE = "";
	Context context;

	public LoginActivityUI(Context parent) {
		this.context = parent;
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this.context);
		// getSharedPreferences("OAUTH_CALL_URL", 0);
		/*
		String callUrl = settings.getString("OAUTH_CALL_URL", null);
		if (callUrl == null) {
			Log.d("Initialization", "Null value found");
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("OAUTH_CALL_URL", LoginActivityUI.OAUTH_CALL_URL);
			editor.commit();
		}
		*/
	}

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		/*
		Button button = (Button) ((Activity) this.context).findViewById(R.id.loginAndAuthorizeBtn);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				//SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivityUI.this);
				String url = GlobalVariables.GetManageDeviceCodeUrl() + GlobalVariables.device_code; 
//				url = url ; //settings.getString("DEVICE_CODE", "");
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(url));
				startActivity(browserIntent);
			}
		});
		//*/
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
