package com.example.AndroidTest;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * User: arne
 * Date: 15.10.13
 * Time: 23:51
 */


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.*;


public class MainActivity extends Activity {
    private static String TAG = "Main Activity";

    ExpandableListView listRight;
    ExpandableListAdapter expandableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listRight = (ExpandableListView) findViewById(R.id.list_right);
        expandableAdapter = new MyExpandableListAdapter(this);
        listRight.setAdapter(expandableAdapter);
    }
}
