package com.example.AndroidTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.HashMap;

/**
 * User: arne
 * Date: 13.10.13
 * Time: 20:18
 */
public class ReadWebpageAsyncTask extends Activity /*implements  LoaderManager.LoaderCallbacks<String> */ {
    private static final String TAG = "ReadWebpageAsyncTask";
    private ListView listView;
    private Button button;

    //Loader<String> loader;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        //loader = getLoaderManager().initLoader(LOADER_ID,null,this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_setings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSearch() {
        Log.v(TAG, "Searching...");
    }

    public void openSettings() {
        Log.v(TAG, "yo Settings here");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate");
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

        getActionBar().setDisplayHomeAsUpEnabled(true);
//        listView = (ListView) findViewById(R.id.url_list);
        final StableArrayAdapter saa = new StableArrayAdapter(this, R.layout.rowlayout, "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile");
        listView.setAdapter(saa);

        listView.setOnItemClickListener(  new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        String item = saa.getItem(position);
                        saa.remove(item);
                        view.setAlpha(1);
                    }
                });
            }
        } );

//        button = (Button) findViewById(R.id.download);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
    }

//    public void onClick(View view) {
//        Log.v(TAG, "onClick");
//        //textView.setText("this text is not from internet");
//        //loader.forceLoad();
//        if(button == view) {
//            Log.d(TAG, "view is button");
//        } else {
//            Log.d(TAG, "oh no, view is not button");
//        }
//    }

    private static String TAG_LOADER_CALLBACKS = "MyLoaderCallbacks";
    public static final int LOADER_ID = 1;

//    @Override
//    public Loader<String> onCreateLoader(int i, Bundle bundle) {
//        switch (i) {
//            case LOADER_ID:
//                Log.d(TAG_LOADER_CALLBACKS, "CreateLoader");
//                return new StringLoader(getBaseContext());
//            default:
//                Log.d(TAG_LOADER_CALLBACKS, "Create Loader with unknown id "+i);
//                return null;
//        }
//    }
//
//    @Override
//    public void onLoadFinished(Loader<String> stringLoader, String s) {
//        switch (stringLoader.getId()) {
//            case LOADER_ID:
//                Log.d(TAG_LOADER_CALLBACKS,"Loader finished loading of: " + s);
//                //textView.setText(s);
//                break;
//            default:
//                Log.d(TAG_LOADER_CALLBACKS,"unknown Loader finished: " + s);
//                break;
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<String> stringLoader) {
//        switch (stringLoader.getId()) {
//            case LOADER_ID:
//                Log.d(TAG_LOADER_CALLBACKS,"Loader reset");
//                break;
//            default:
//                Log.d(TAG_LOADER_CALLBACKS,"unknown Loader reset" + stringLoader.getId());
//                break;
//        }
//    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String,Integer> map = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int resourceId, String... objects) {
            super(context, resourceId, objects);
            for( int i = 0; i < objects.length; i++) {
                map.put(objects[i], i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return map.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
