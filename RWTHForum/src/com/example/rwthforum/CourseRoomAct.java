package com.example.rwthforum;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class CourseRoomAct extends Activity {
	
	public final static String TAG = "CourseRoomAct";
	private static SharedPreferences sp;
	private SharedPreferences.Editor edtr;
	private ListView coursesList;
	private ArrayList<CourseRowData> arrayList;
	private LayoutInflater layoutInflater;
	
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
		
		getCourses();
		
				
	}
	


	private void getCourses()
	{
		String token=sp.getString("access_token", null);
		GetCoursesTask getCoursesTask=new GetCoursesTask(this);
		Log.d(TAG, "goint to start loading task");
		try{
			getCoursesTask.execute(token);
		}
		catch(Exception e)
		{
			Log.d(TAG, "Runtime exception ");
			getCoursesTask.cancel(true);
			Toast.makeText(this, "Could not connect to the server", Toast.LENGTH_LONG).show();
		}

	}



	private void initialize() {
		Log.d(TAG, "in Initialize");
		// TODO Auto-generated method stub
		TextView header=(TextView)findViewById(R.id.title);
		header.setText(R.string.your_courses);
		coursesList=(ListView)findViewById(R.id.list);
		arrayList=new ArrayList<CourseRowData>();
		this.layoutInflater=LayoutInflater.from(this);
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
		mi.inflate(R.menu.course_room, menu);
		//ActionBar actionBar=getActionBar();
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.refresh:
			
			getCourses();
			Log.d(TAG, "onRefresh");
			return true;
			
		
		}
		return false;
	}
	

	public static class CourseRowHolder//static so that it could be imported by any other app and cud be used
	{
		public TextView title;
		public TextView body;
		public CourseRowData rowData;
	}
	
	public void setList(ArrayList<CourseRowData> list)
	{
		Log.d(TAG, "setList");
		this.arrayList=list;
		Log.d(TAG, "added rowdata to list");
		Log.d(TAG, "setting Adapter");
		this.coursesList.setAdapter(new CourseRowDataAdapter(this,layoutInflater,this.arrayList));
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
