package com.example.AndroidTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;
import java.util.Map;

/**
 * User: arne
 * Date: 17.11.13
 * Time: 15:17
 */
public class ThreeLevelListActivity extends Activity {
    ExpandableListView parent_list;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        parent_list = (ExpandableListView) findViewById(R.id.parent_list);
        parent_list.setAdapter(new ParentListAdapter(StaticData.getGroups()));
    }

    class ParentListAdapter extends BaseExpandableListAdapter {
        List<StaticData.Group> groups;

        public ParentListAdapter(List<StaticData.Group> groups) {
            this.groups = groups;
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
        public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup viewGroup) {
            CustExpListview SecondLevelexplv = new CustExpListview(ThreeLevelListActivity.this);
            SecondLevelexplv.setAdapter(new SecondLevelAdapter(groups.get(groupPos).topics.get(childPos)));
            SecondLevelexplv.setGroupIndicator(null);
            return SecondLevelexplv;
        }

        @Override
        public int getChildrenCount(int i) {
            return 3;
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
            return groups.size();
        }

        @Override
        public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_group, null);
            }

            TextView header_text = (TextView) convertView.findViewById(R.id.header_text);
            header_text.setText(groups.get(i).name);

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

    public class CustExpListview extends ExpandableListView {
        public CustExpListview(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(10000, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {
        StaticData.Topic topic;

        public SecondLevelAdapter(StaticData.Topic topic) {
            this.topic = topic;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if( convertView == null ) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, null);
            }

            TextView top_text = (TextView)convertView.findViewById(R.id.top_text);
            TextView bottom_text = (TextView)convertView.findViewById(R.id.bottom_text);

            StaticData.Link link = topic.links.get(childPosition);

            top_text.setText(link.description);
            bottom_text.setText(link.url);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return topic.links.size();
        }


        @Override
        public Object getGroup(int groupPosition) {
            return groupPosition;
        }


        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_group, null);
            }

            TextView tv = (TextView) convertView.findViewById(R.id.header_text);
            tv.setText(topic.name);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
