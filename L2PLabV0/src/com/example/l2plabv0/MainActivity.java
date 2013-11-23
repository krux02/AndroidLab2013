package com.example.l2plabv0;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(MainActivity.this, ProgressSandbox.class);
		MainActivity.this.startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		CommonData.writeLog("onResume", "Main activity block was called");
		Intent intent = new Intent(MainActivity.this, ProgressSandbox.class);
		MainActivity.this.startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;// super.onCreateOptionsMenu(menu);
	}

}
