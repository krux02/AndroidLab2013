package com.example.AndroidTest;

import android.content.Context;
import android.database.MatrixCursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.*;

/**
 * User: arne
 * Date: 18.10.13
 * Time: 20:19
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    public static String TAG = "MyExpandableListAdapter";

    List<Integer> categoryColors  = new ArrayList<Integer>();
    List<UrlCategory> data;
    Context context;

    MyExpandableListAdapter(Context context, List<List<String>> data) {
        this.context = context;
        this.data = UrlBuilder.buildCategories(data);
        Random r = new Random();

        Log.d(TAG, this.data.toString());
        for(UrlCategory ignored : this.data) categoryColors.add((r.nextInt() & 0x3f3f3f) | 0xff000000);

    }

    MyExpandableListAdapter(Context context) {
        this(context, StaticData.getData());
    }

    @Override
    public int getGroupCount() {
        return  data.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return data.get(i).getCourse().size();
    }

    @Override
    public String getGroup(int i) {
        return data.get(i).getName();
    }

    @Override
    public UrlTopic getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getCourse().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        convertView.setBackgroundColor(categoryColors.get(groupPosition) + 0x101010);

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.header_text);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        UrlTopic entry = data.get(groupPosition).getCourse().get(childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.top_text);
        TextView bottomChild = (TextView) convertView.findViewById(R.id.bottom_text);
        convertView.setBackgroundColor(categoryColors.get(groupPosition));
        txtListChild.setText(entry.getName());
        bottomChild.setText(entry.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}