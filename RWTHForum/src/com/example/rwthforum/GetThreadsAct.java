package com.example.rwthforum;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GetThreadsAct extends Activity {
	public final static String TAG = "GetThreadsAct";
	private static SharedPreferences sp;
	private SharedPreferences.Editor edtr;
	private ListView threadsList;
	private ArrayList<ThreadRowData> arrayList;
	private LayoutInflater layoutInflater;
	private String courseId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_course_room);
		Log.d(TAG, "onCreate");
		initialize();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume");
		getThreads();

	}

	private void getThreads() {
		String userToken = sp.getString("access_token", null);
		GetThreadsTask getThreadsTask = new GetThreadsTask(this);
		Log.d(TAG, "goint to start loading task");
		try {
			getThreadsTask.execute(userToken, courseId);
		} catch (Exception e) {
			Log.d(TAG, "Runtime exception ");
			getThreadsTask.cancel(true);
			Toast.makeText(this, "Could not connect to the server",
					Toast.LENGTH_LONG).show();
		}

	}

	private void initialize() {
		Log.d(TAG, "in Initialize");
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		courseId = bundle.getString("courseId");
		TextView header=(TextView)findViewById(R.id.title);
		header.setText(R.string.threads);
		threadsList = (ListView) findViewById(R.id.list);
		arrayList = new ArrayList<ThreadRowData>();
		this.layoutInflater = LayoutInflater.from(this);
		sp = getSharedPreferences("RWTH_File", 0);
		edtr = sp.edit();
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		Log.d(TAG, "onCreateOptionsMenu");
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.thread, menu);
		// ActionBar actionBar=getActionBar();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.new_thread:
			Intent i=new Intent(this,AddThreadAct.class);
			i.putExtra("courseId", courseId);
			this.startActivity(i);
			return true;

		case R.id.refresh:

			getThreads();
			Log.d(TAG, "onRefresh");
			return true;

		case R.id.course_room:
			Intent i1=new Intent(this,CourseRoomAct.class);
			this.startActivity(i1);
			return true;

		}
		return false;
	}

	public static class ThreadRowHolder// static so that it could be imported by
										// any other app and cud be used
	{
		public TextView title;
		public TextView body;
		public Button reply;
		public ThreadRowData rowData;
		

	}

	public void setList(ArrayList<ThreadRowData> list) {
		Log.d(TAG, "setList");
		this.arrayList = list;
		Log.d(TAG, "added rowdata to list");
		Log.d(TAG, "setting Adapter");
		this.threadsList.setAdapter(new ThreadRowDataAdapter(this,
				layoutInflater, this.arrayList, courseId));

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
