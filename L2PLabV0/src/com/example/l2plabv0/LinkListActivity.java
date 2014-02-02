package com.example.l2plabv0;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    
    private class DownloadFilesTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return "13ws-2014-ApiCalled";
        }


        protected void onPostExecute(String title) {
        	Window w= getWindow();
        	w.setTitle(title);
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threelevellisthead);

        parent_list = (ExpandableListView) findViewById(R.id.parent_list);
        registerForContextMenu(parent_list);
        courseList = StaticData.getCourseList();
        adapter = new LinkListAdapter(this, courseList);
        parent_list.setAdapter(adapter);
        parent_list.setOnChildClickListener(adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        //Log.d(TAG,"Create");
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("TITLE");
            setTitle(value);
        }else
        {
        	String semesterNameApi = "http://137.226.231.116/websites/ws2014/_vti_bin/L2PServices/API.svc/v1/viewCourseInfo?accessToken=asdlk&cid=13ws-55503";
        	new DownloadFilesTask().execute(semesterNameApi);
        }
        CommonData.writeLog("onCreate", "link list activity");
    }
    
        
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
        	Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent
                        .startActivities();
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                //NavUtils.navigateUpTo(this, upIntent);
            	upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(upIntent);
            	finish();
            }
            return true;

        }
        return super.onOptionsItemSelected(item);
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
        final int courseId = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        final StaticData.Course course = courseList.get(courseId);
        switch (ExpandableListView.getPackedPositionType(info.packedPosition)) {
            case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
                Log.d(TAG,"child");
                final int linkId = ExpandableListView.getPackedPositionChild(info.packedPosition);
                final StaticData.Link link = course.topics.get(linkId);
                getMenuInflater().inflate(R.menu.link_menu, menu);
                menu.setHeaderTitle(link.name);
                MenuItem remove = menu.findItem(R.id.remove);
                remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        course.topics.remove(linkId);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });
                MenuItem rename = menu.findItem(R.id.edit);
                rename.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent myIntent = new Intent(LinkListActivity.this, LinkFormActivity.class);
                        myIntent.putExtra("courseId", courseId);
                        myIntent.putExtra("courseName", course.name );
                        myIntent.putExtra("linkId", linkId);
                        myIntent.putExtra("link", link);
                        startActivityForResult(myIntent, REQUEST_CODE_EDIT_LINK);
                        return true;
                    }
                });
                break;
            case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
                Log.d(TAG,"courseId");
                getMenuInflater().inflate(R.menu.group_menu, menu);
                menu.setHeaderTitle(course.name);
                MenuItem add = menu.findItem(R.id.add);
                add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent myIntent = new Intent(LinkListActivity.this, LinkFormActivity.class);
                        myIntent.putExtra("courseId",courseId);
                        myIntent.putExtra("courseName", course.name);
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
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT_LINK) {
            StaticData.Link link = (StaticData.Link)data.getSerializableExtra("link");
            int courseId = data.getIntExtra("courseId",-1);
            int linkId = data.getIntExtra("linkId",-1);
            StaticData.Course course = courseList.get(courseId);
            course.topics.set(linkId,link);
            Collections.sort(course.topics, mycomparator);
            adapter.notifyDataSetChanged();
        } else if (resultCode == RESULT_CANCELED) {
            Log.d(TAG, "cancel new link intent");
        } else {
            Log.d(TAG, "result: " + resultCode + " request " + requestCode);
        }
    }
}
