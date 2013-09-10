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
import com.example.rwthforum.CourseRoomAct.CourseRowHolder;

public class CourseRowDataAdapter extends BaseAdapter implements
		OnClickListener {

	public final static String TAG = "CourseRowDataAdapter";
	private CourseRoomAct activity;
	private LayoutInflater layoutInflater;
	private ArrayList<CourseRowData> arrayList = null;

	public CourseRowDataAdapter(CourseRoomAct activity, LayoutInflater l,
			ArrayList<CourseRowData> arrayList) {
		// TODO Auto-generated constructor stub
		Log.d(TAG, "constructor");
		this.activity = activity;
		this.layoutInflater = l;
		this.arrayList = arrayList;
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
		Log.d(TAG, "getItem");
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
		CourseRowHolder rowHolder;
		Log.d(TAG, "getView");
		if (convertView == null) {

			Log.d(TAG, "left Layout");
			convertView = layoutInflater.inflate(R.layout.lay_course_row,
					parent, false);
		
			rowHolder = new CourseRowHolder();
			rowHolder.title = (TextView) convertView
					.findViewById(R.id.headingL);
			rowHolder.body = (TextView) convertView.findViewById(R.id.bodyL);
			
			convertView.setTag(rowHolder);
		

		} else {
			rowHolder = (CourseRowHolder) convertView.getTag();
		}
		convertView.setOnClickListener(this);
		CourseRowData rowData = arrayList.get(position);
		rowHolder.rowData=rowData;
		Log.d(TAG, "setting data");
		String title = rowData.getTitle().toString();
		String id = rowData.getID().toString();
		rowHolder.title.setText(id);
		rowHolder.body.setText(title);
		Log.d(TAG, "setting data " + title + " " + id);
		// row.ID=rowData.getMasterId();//check which is thread.............
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		CourseRowHolder row = (CourseRowHolder) v.getTag();
		if(v instanceof Button)
		{
			
		}
		else{
			String ID = row.title.getText().toString();// gives CourseId
			Log.d(TAG, "id from a= " + ID);
			Intent i = new Intent(this.activity, GetThreadsAct.class);
			i.putExtra("courseId", ID);
			this.activity.startActivity(i);
		}
		

	}

}
