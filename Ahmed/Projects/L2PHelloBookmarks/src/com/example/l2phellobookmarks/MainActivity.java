package com.example.l2phellobookmarks;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LoginActivityUI obj = new LoginActivityUI(MainActivity.this);
		new LoginManager(MainActivity.this, obj)
				.execute("http://10.0.2.2/oAuth_req.php");
		this.sharedPrefManagerCode();
		
		Button button = (Button) findViewById(R.id.loginAndAuthorizeBtn);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				//SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivityUI.this);
				String url = GlobalVariables.GetManageDeviceCodeUrl() + GlobalVariables.user_code; 
				Log.d("onOAuthLinkClick",url);
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(url));
				startActivity(browserIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;

	}
	
	//*
	private void sharedPrefManagerCode(){
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		//settings.edit().remove("expires_in").commit();
		//settings.edit().remove("OAUTH_CALL_URL").commit();
		Map<String,?> keys = settings.getAll();

		for(Map.Entry<String,?> entry : keys.entrySet()){
		            Log.d("map values",entry.getKey() + ": " + 
		                                   entry.getValue().toString());            
		 }
	}
	//*/

}
