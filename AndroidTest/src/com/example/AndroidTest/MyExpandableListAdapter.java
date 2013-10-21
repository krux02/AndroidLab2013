package com.example.AndroidTest;

import android.content.Context;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * User: arne
 * Date: 18.10.13
 * Time: 20:19
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    MatrixCursor data;

    ArrayList<Integer> groupColors;
    ArrayList<String> groupNames;
    ArrayList<ArrayList<Integer>> groupId2Topic;
    Context context;

    private static void setMatrixLine(MatrixCursor matrix, int line) {
        while(matrix.getPosition() < line)
            matrix.moveToNext();
        while(matrix.getPosition() > line)
            matrix.moveToPrevious();
    }

    MyExpandableListAdapter(Context context, MatrixCursor data) {
        this.context = context;
        this.data = data;


        LinkedHashMap<String, Integer> group2topicsId = new LinkedHashMap<String, Integer>();
        groupId2Topic = new ArrayList<ArrayList<Integer>>();
        groupNames = new ArrayList<String>();
        groupColors = new ArrayList<Integer>();

        Random r = new Random();

        data.moveToFirst();
        while (!data.isAfterLast()) {
            String groupName = data.getString(0);
            if( !group2topicsId.containsKey(groupName) ) {
                group2topicsId.put(groupName, groupNames.size());
                groupNames.add(groupName);
                groupId2Topic.add(new ArrayList<Integer>());
                groupColors.add(Color.rgb(r.nextInt() % 84, r.nextInt() % 84, r.nextInt() % 84));
            }
            int id = group2topicsId.get(groupName);
            assert(groupNames.get(id).compareTo(groupName) == 0);
            groupId2Topic.get(id).add(data.getPosition());
            data.moveToNext();
        }
    }

    MyExpandableListAdapter(Context context) {
        this(context, getData());
    }

    private static MatrixCursor getData() {
        MatrixCursor mat = new MatrixCursor(new String[]{"group", "topic", "description", "url"});
        mat.addRow(new String[]{"course", "course Topic 1", "bla bla",   "www.example.com"});
        mat.addRow(new String[]{"course", "course Topic 1", "blub blub", "www.example.com"});
        mat.addRow(new String[]{"course", "course Topic 2", "Beispiel", "www.example.com"});
        mat.addRow(new String[]{"course", "course Topic 2", "Bleistift", "www.example.com"});
        mat.addRow(new String[]{"course", "course Topic 3", "foo", "www.example.com"});
        mat.addRow(new String[]{"course", "course Topic 3", "bar", "www.example.com"});
        mat.addRow(new String[]{"group", "group Topic 1", "foobar",   "www.example.com"});
        mat.addRow(new String[]{"group", "group Topic 1", "baz", "www.example.com"});
        mat.addRow(new String[]{"group", "group Topic 2", "lolo", "www.example.com"});
        mat.addRow(new String[]{"group", "group Topic 2", "Pizza", "www.example.com"});
        mat.addRow(new String[]{"group", "group Topic 3", "Wurst", "www.example.com"});
        mat.addRow(new String[]{"group", "group Topic 3", "Brot", "www.example.com"});
        return mat;
    }


    @Override
    public int getGroupCount() {
        return  groupNames.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return groupId2Topic.get(i).size();
    }

    @Override
    public String getGroup(int i) {
        return groupNames.get(i);
    }

    @Override
    public Integer getChild(int groupPosition, int childPosition) {
        return groupId2Topic.get(groupPosition).get(childPosition);
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

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Integer line = getChild(groupPosition, childPosition);
        setMatrixLine(data,line);
        String childText = data.getString(2) + data.getString(3);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        txtListChild.setBackgroundColor(groupColors.get(groupPosition));
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


