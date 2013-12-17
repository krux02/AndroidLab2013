package com.example.l2plabv0;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.List;

/**
 * User: arne
 * Date: 17.11.13
 * Time: 15:17
 */
public class LinkListActivity extends Activity {
    static String TAG = "LinkListActivity";
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

                MenuItem add = menu.findItem(R.id.add);
                add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        return true;
                    }
                });

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
                menu.clear();
                //menu.setHeaderTitle(courseList.get(group).name);
                break;
        }
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
}
