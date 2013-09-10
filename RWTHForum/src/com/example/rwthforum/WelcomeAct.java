package com.example.rwthforum;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//import android.content.SharedPreferences;

public class WelcomeAct extends Activity implements OnClickListener {
	public final static String TAG = "WelcomeAct";
	private SharedPreferences sp;
	private SharedPreferences.Editor edtr;
	private Button enter;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_welcome);
		Log.d(TAG, "onCreate");
		initialize();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	private void initialize() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("RWTH_File", 0);
		edtr = sp.edit();
		enter = (Button) findViewById(R.id.bEnter);
		enter.setOnClickListener(this);
		enter.setTag("enter");

	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getTag().equals("enter")) {
			Intent i = new Intent(WelcomeAct.this, CourseRoomAct.class);
			startActivity(i);
		}

	}

}
