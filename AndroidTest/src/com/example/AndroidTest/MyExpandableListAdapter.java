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
        this(context, getData());
    }

    private static ArrayList<List<String>> getData() {
        String[] names = new String[]{"group", "topic", "description", "url"};
        ArrayList<List<String>> data = new ArrayList<List<String>>();
        data.add(Arrays.asList("course", "course Topic 1", "bla bla", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 1", "blub blub", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 2", "Beispiel", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 2", "Bleistift", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 3", "foo", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 3", "bar", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 1", "foobar", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 1", "baz", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 2", "lolo", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 2", "Pizza", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 3", "Wurst", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 3", "Brot", "www.example.com"));
        return data;
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

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
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

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView bottomChild = (TextView) convertView.findViewById(R.id.bottom_list);
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

class SubListAdapter extends BaseExpandableListAdapter {

    @Override
    public int getGroupCount() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getGroup(int i) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getGroupId(int i) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getChildId(int i, int i2) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasStableIds() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}