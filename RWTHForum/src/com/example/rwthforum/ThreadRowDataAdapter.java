package com.example.rwthforum;

import java.util.ArrayList;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.rwthforum.GetThreadsAct.ThreadRowHolder;

public class ThreadRowDataAdapter extends BaseAdapter implements
		OnClickListener {
	public final static String TAG = "ThreadRowDataAdapter";
	private GetThreadsAct activity;
	private LayoutInflater layoutInflater;
	private ArrayList<ThreadRowData> arrayList = null;
	private String courseId;

	public ThreadRowDataAdapter(GetThreadsAct activity, LayoutInflater l,
			ArrayList<ThreadRowData> arrayList, String courseId) {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "constructor");
		this.activity = activity;
		this.layoutInflater = l;
		this.arrayList = arrayList;
		this.courseId = courseId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d(TAG, "getCount");
		try {
			return this.arrayList.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		Log.d(TAG, "getItemId");
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ThreadRowHolder rowHolder;
		Log.d(TAG, "getView");
		if (convertView == null) {

			Log.d(TAG, "left Layout");
			convertView = layoutInflater.inflate(R.layout.lay_thread_row,
					parent, false);
			rowHolder = new ThreadRowHolder();
			rowHolder.title = (TextView) convertView
					.findViewById(R.id.headingL);
			rowHolder.body = (TextView) convertView.findViewById(R.id.bodyL);
			rowHolder.reply=(Button) convertView.findViewById(R.id.reply);
			rowHolder.rowData = null;
			convertView.setTag(rowHolder);
			rowHolder.reply.setTag(rowHolder);

		} else {
			rowHolder = (ThreadRowHolder) convertView.getTag();
		}
		convertView.setOnClickListener(this);
		rowHolder.reply.setOnClickListener(this);
		ThreadRowData rowData = arrayList.get(position);
		rowHolder.rowData=rowData;
		Log.d(TAG, "setting data");
		String title = rowData.getTitle();
		String body = rowData.getBody();
		int id = rowData.getID();
		rowHolder.title.setText(title);
		rowHolder.body.setText(body);
		Log.d(TAG, "setting data " + title + " " + body);
		// row.ID=rowData.getMasterId();//check which is thread.............
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ThreadRowHolder row = (ThreadRowHolder) v.getTag();
		String threadId = row.rowData.getID()+"";
		Log.d(TAG, "id from a= " + threadId);
		if(v instanceof Button)
		{
			Intent i=new Intent(this.activity,AddPostAct.class);
			i.putExtra("courseId", courseId);
			i.putExtra("parentPostId", threadId);
			this.activity.startActivity(i);
		}
		else
		{

			Intent i = new Intent(this.activity, GetPostsAct.class);
			i.putExtra("courseId", courseId);
			i.putExtra("threadId", threadId);
			this.activity.startActivity(i);
		}


	}

}
