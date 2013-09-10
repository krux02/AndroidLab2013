package com.example.rwthforum;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddThreadAct extends Activity implements OnClickListener{
	public final static String TAG = "AddThreadAct";
	private EditText thread_title,thread_body;
	private Button submit;
	private String courseId,userToken;
	private static SharedPreferences sp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_add_thread);
		Log.d(TAG, "onCreate");
		initialize();
	}
	private void initialize() {
		Log.d(TAG, "in Initialize");
		// TODO Auto-generated method stub
		thread_title=(EditText)findViewById(R.id.thread_title);
		thread_body=(EditText)findViewById(R.id.thread_body);
		submit=(Button)findViewById(R.id.submit);
		submit.setOnClickListener(this);
		courseId=getIntent().getExtras().getString("courseId");
		Log.d(TAG,"courseId retrieved= "+courseId);
		sp = getSharedPreferences("RWTH_File", 0);
		userToken=sp.getString("access_token", null);
		Log.d(TAG,"userToken retrieved= "+userToken);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d(TAG, "onResume");
		
	}
	public void thread_added(int ID)
	{
		Log.d(TAG, "thread_added");
		//send back to the getThreads Page so that user could see the changes
		Toast.makeText(this, R.string.thread_added, Toast.LENGTH_LONG).show();
		
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
		if(thread_title.getText().toString()==null)
			Toast.makeText(this, R.string.title_null, Toast.LENGTH_LONG).show();
		else
		{
			String title=thread_title.getText().toString();
			String body=thread_body.getText().toString();
			AddThreadTask addThreadTask=new AddThreadTask(this);
			Log.d(TAG, "goint to start loading task");
			try{
				addThreadTask.execute(userToken,courseId,title,body);
			}
			catch(Exception e)
			{
				Log.d(TAG, "Runtime exception ");
				addThreadTask.cancel(true);
				Toast.makeText(this, "Could not connect to the server", Toast.LENGTH_LONG).show();
			}
		}
		
	}

}
