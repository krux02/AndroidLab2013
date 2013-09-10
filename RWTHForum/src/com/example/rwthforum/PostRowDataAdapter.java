package com.example.rwthforum;

import java.util.ArrayList;

import com.example.rwthforum.CourseRoomAct.CourseRowHolder;
import com.example.rwthforum.GetPostsAct.PostRowHolder;
import com.example.rwthforum.GetThreadsAct.ThreadRowHolder;

import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

public class PostRowDataAdapter extends BaseAdapter implements
		OnClickListener {
	public final static String TAG = "PostRowDataAdapter";
	private GetPostsAct activity;
	private LayoutInflater layoutInflater;
	private ArrayList<PostRowData> arrayList = null;
	private String courseId;

	public PostRowDataAdapter(GetPostsAct activity, LayoutInflater l,
			ArrayList<PostRowData> arrayList, String courseId) {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "constructor");
		this.activity = activity;
		this.layoutInflater = l;
		this.arrayList = arrayList;
		this.courseId=courseId;
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
		PostRowHolder rowHolder;
		Log.d(TAG, "getView");
		if (convertView == null) {

			Log.d(TAG, "left Layout");
			convertView = layoutInflater.inflate(R.layout.lay_post_row,
					parent, false);
			rowHolder = new PostRowHolder();
			rowHolder.ThreadIndex = (TextView) convertView
					.findViewById(R.id.headingL);
			rowHolder.body = (TextView) convertView.findViewById(R.id.bodyL);
			rowHolder.reply=(Button) convertView.findViewById(R.id.reply);
			rowHolder.rowData = null;
			convertView.setTag(rowHolder);
			rowHolder.reply.setTag(rowHolder);

		} else {
			rowHolder = (PostRowHolder) convertView.getTag();
		}
		//convertView.setOnClickListener(this);
		rowHolder.reply.setOnClickListener(this);
		PostRowData rowData = arrayList.get(position);
		rowHolder.rowData=rowData;
		Log.d(TAG, "setting data");
		String threadIndex = rowData.getThreadIndex();
		String body = rowData.getBody();
		int id = rowData.getID();
		int ParentThreadId=rowData.getParentThreadId();
		//rowHolder.ThreadIndex.setText(threadIndex);
		rowHolder.body.setText(body);
		Log.d(TAG, "setting data threadIndex: " + threadIndex + "body: " + body+ "id: " + id+ "ParentThreadId: " +ParentThreadId);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		PostRowHolder row = (PostRowHolder) v.getTag();
		String threadId = row.rowData.getParentThreadId()+"";
		Log.d(TAG, "id from a= " + threadId);
		if (v instanceof Button) {
			
			Intent i=new Intent(this.activity,AddPostAct.class);
			i.putExtra("courseId", courseId);
			i.putExtra("parentPostId", threadId);
			this.activity.startActivity(i);
		}
		else
		{
			
		}

	}


}
