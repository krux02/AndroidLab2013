package com.example.l2plabv0;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.List;

/**
 * User: arne
 * Date: 17.11.13
 * Time: 15:17
 */
public class LinkListActivity extends Activity {
    static String TAG = "LinkListActivity";
    ExpandableListView parent_list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_threelevellisthead);
        parent_list = (ExpandableListView) findViewById(R.id.parent_list);
        parent_list.setAdapter(new ParentListAdapter(StaticData.getGroup()));
    }

    class ParentListAdapter extends BaseExpandableListAdapter {
        List<StaticData.Course> courses;

        public ParentListAdapter(List<StaticData.Course> courses) {
            this.courses = courses;
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
            return 3;
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick");
            }
        };

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if( convertView == null ) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, null);
            }

            TextView top_text = (TextView)convertView.findViewById(R.id.top_text);
            TextView bottom_text = (TextView)convertView.findViewById(R.id.bottom_text);
            ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.favorite);

            imageButton.setOnClickListener(listener);

            StaticData.Link link = courses.get(groupPosition).topics.get(childPosition);
            top_text.setText(link.description);
            bottom_text.setText(link.url);
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
                convertView = getLayoutInflater().inflate(R.layout.list_group, null);
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
}
