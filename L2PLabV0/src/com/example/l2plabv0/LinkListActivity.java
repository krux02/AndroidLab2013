package com.example.l2plabv0;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.security.acl.Group;
import java.util.Collections;
import java.util.Comparator;
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
        registerForContextMenu(parent_list);
        ParentListAdapter adapter = new ParentListAdapter(StaticData.getGroup());
        parent_list.setAdapter(adapter);
        parent_list.setOnChildClickListener(adapter);


        Log.d(TAG,"Create");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.link_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info =
                (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.add:
                return true;
            case R.id.remove:
                return true;
            case R.id.rename:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    class ParentListAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {
        List<StaticData.Course> courses;

        public ParentListAdapter(List <StaticData.Course> courses) {
            this.courses = courses;
        }

        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int course, int link_id, long l) {
            Log.d(TAG,"onChildClick");
            StaticData.Link link = courses.get(course).topics.get(link_id);
            String url = link.url;

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

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
            return 3;
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
                convertView = getLayoutInflater().inflate(R.layout.list_item, null);
            }

            TextView top_text = (TextView)convertView.findViewById(R.id.top_text);
            TextView bottom_text = (TextView)convertView.findViewById(R.id.bottom_text);
            final ImageButton favorite = (ImageButton)convertView.findViewById(R.id.favorite);
            final StaticData.Course course = courses.get(groupPosition);
            final StaticData.Link link = course.topics.get(childPosition);
            favorite.setOnClickListener(new View.OnClickListener() {
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
                    ParentListAdapter.this.notifyDataSetChanged();
                }
            });
            if(link.isBookmarked) {
                favorite.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                favorite.setImageResource(android.R.drawable.btn_star_big_off);
            }

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
