package com.example.l2plabv0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

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
    final static int REQUEST_CODE_NEW_LINK = 1;
    final static int REQUEST_CODE_EDIT_LINK = 2;
    ExpandableListView parent_list;
    List<StaticData.Course> courseList;
    LinkListAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threelevellisthead);

        parent_list = (ExpandableListView) findViewById(R.id.parent_list);
        registerForContextMenu(parent_list);
        courseList = StaticData.getCourseList();
        adapter = new LinkListAdapter(this, courseList);
        parent_list.setAdapter(adapter);
        parent_list.setOnChildClickListener(adapter);

        Log.d(TAG,"Create");
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);


        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo)menuInfo;
        final int group = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        switch (ExpandableListView.getPackedPositionType(info.packedPosition)) {
            case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
                Log.d(TAG,"child");
                final int child = ExpandableListView.getPackedPositionChild(info.packedPosition);
                getMenuInflater().inflate(R.menu.link_menu, menu);
                menu.setHeaderTitle(courseList.get(group).topics.get(child).description);
                MenuItem remove = menu.findItem(R.id.remove);
                remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        courseList.get(group).topics.remove(child);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });
                MenuItem rename = menu.findItem(R.id.rename);
                rename.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return true;
                    }
                });
                break;
            case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
                Log.d(TAG,"group");
                getMenuInflater().inflate(R.menu.group_menu, menu);
                menu.setHeaderTitle(courseList.get(group).name);
                MenuItem add = menu.findItem(R.id.add);
                add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent myIntent = new Intent(LinkListActivity.this, LinkFormActivity.class);
                        myIntent.putExtra("courseId",group);
                        myIntent.putExtra("courseName", courseList.get(group).name);
                        startActivityForResult(myIntent, REQUEST_CODE_NEW_LINK);
                        return true;
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_NEW_LINK) {
            if (data.hasExtra("link") && data.hasExtra("courseId")) {
                StaticData.Link link = (StaticData.Link)data.getSerializableExtra("link");
                int courseId = data.getIntExtra("courseId",-1);

                StaticData.Course course = courseList.get(courseId);
                course.topics.add(link);
                Collections.sort(course.topics, mycomparator);
                adapter.notifyDataSetChanged();
            } else {
                Log.d(TAG, "link: " + data.hasExtra("link"));
                Log.d(TAG, "courseId: " + data.hasExtra("courseId"));
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.d(TAG, "cancel new link intent");
        } else {
            Log.d(TAG, "result: " + resultCode + " request " + requestCode);
        }
    }
}
