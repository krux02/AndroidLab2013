package com.example.l2plabv0;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * User: arne
 * Date: 17.12.13
 * Time: 01:44
 */
public class LinkListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {
    public static String TAG = "LinkListAdapter";

    List<StaticData.Course> courses;
    int[] groupColors;
    int[] groupSubColors;
    LinkListActivity activity;

    public LinkListAdapter(LinkListActivity activity, List <StaticData.Course> courses) {
        this.activity = activity;
        this.courses = courses;

        groupColors = new int[courses.size()];
        groupSubColors = new int[courses.size()];
        Random rnd = new Random();
        float[] hsv = new float[]{0f, 1f, 0.5f};

        for( int i = 0; i < groupColors.length; i++ ) {
            hsv[0] = rnd.nextFloat() * 360;
            hsv[1] = 0.75f;
            groupColors[i] = Color.HSVToColor(hsv);
            hsv[1] = 0.50f;
            groupSubColors[i] = Color.HSVToColor(hsv);
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int course, int link_id, long l) {
        Log.d(TAG, "onChildClick");
        StaticData.Link link = courses.get(course).topics.get(link_id);
        String url = link.url;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        activity.startActivity(i);
        return true;
    }

    @Override
    public Object getChild(int i, int i2) {
        return i2; //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long getChildId(int i, int i2) {
        return i2; //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getChildrenCount(int i) {
        return courses.get(i).topics.size();
    }

    final Comparator<StaticData.Link> mycomparator = new Comparator<StaticData.Link>() {
        @Override
        public int compare(StaticData.Link link1, StaticData.Link link2) {
            if (link1.isBookmarked == link2.isBookmarked)
                return 0;
            else if(link1.isBookmarked)
                return -1;
            else
                return 1;
        }
    };

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item, null);
        }
        convertView.setBackgroundColor(groupSubColors[groupPosition]);

        TextView top_text = (TextView)convertView.findViewById(R.id.top_text);
        TextView bottom_text = (TextView)convertView.findViewById(R.id.bottom_text);
        final ImageButton favorite = (ImageButton)convertView.findViewById(R.id.favorite);
        final StaticData.Course course = courses.get(groupPosition);
        final StaticData.Link link = course.topics.get(childPosition);

        View.OnClickListener favoriteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (link.isBookmarked) {
                    favorite.setImageResource(android.R.drawable.btn_star_big_off);
                    link.isBookmarked = false;
                    
                } else {
                    favorite.setImageResource(android.R.drawable.btn_star_big_on);
                    link.isBookmarked = true;
                }
                Collections.sort(course.topics, mycomparator);
                // sorting list
                LinkListAdapter.this.notifyDataSetChanged();
            }
        };
        favorite.setOnClickListener(favoriteClickListener);


        if( link.isBookmarked ) {
            favorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            favorite.setImageResource(android.R.drawable.btn_star_big_off);
        }

        top_text.setText(link.name);
        bottom_text.setText(link.description);
        return convertView;
    }

    @Override
    public Object getGroup(int i) {
        return i;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public int getGroupCount() {
        return courses.size();
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_group, null);
            convertView.setBackgroundColor(groupColors[i]);
        }
        TextView header_text = (TextView) convertView.findViewById(R.id.header_text);
        header_text.setText(courses.get(i).name);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }
}
