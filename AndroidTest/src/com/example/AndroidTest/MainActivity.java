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

    ListView listLeft;
    ExpandableListView listRight;

    MySimpleArrayAdapter adapter;
    ExpandableListAdapter expandableAdapter;
    List<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        listLeft = (ListView) findViewById(R.id.list_left);
        listRight = (ExpandableListView) findViewById(R.id.list_right);

        list = getModel();
        adapter = new MySimpleArrayAdapter(this, list);
        expandableAdapter = new MyExpandableListAdapter(this);

        listLeft.setAdapter(adapter);
        listRight.setAdapter(expandableAdapter);
    }

    private List<Model> getModel() {
        List<Model> list = new ArrayList<Model>();
        list.add(get("Linux"));
        list.add(get("Windows7"));
        list.add(get("Suse"));
        list.add(get("Eclipse"));
        list.add(get("Ubuntu"));
        list.add(get("Solaris"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        list.add(get("Linux"));
        list.add(get("Windows7"));
        list.add(get("Suse"));
        list.add(get("Eclipse"));
        list.add(get("Ubuntu"));
        list.add(get("Solaris"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        list.add(get("Linux"));
        list.add(get("Windows7"));
        list.add(get("Suse"));
        list.add(get("Eclipse"));
        list.add(get("Ubuntu"));
        list.add(get("Solaris"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        list.add(get("Linux"));
        list.add(get("Windows7"));
        list.add(get("Suse"));
        list.add(get("Eclipse"));
        list.add(get("Ubuntu"));
        list.add(get("Solaris"));
        list.add(get("Android"));
        list.add(get("iPhone"));
        // Initially select one of the items
        list.get(1).setSelected(true);
        return list;
    }

    private Model get(String s) {
        return new Model(s);
    }

    public void onClick(View view) {
        RelativeLayout layout = (RelativeLayout) view;
        ListView parent = (ListView) layout.getParent();
        MySimpleArrayAdapter adapter = (MySimpleArrayAdapter) parent.getAdapter();

        int position = parent.getPositionForView(layout);
        Model model = adapter.getItem(position);
        model.setSelected(!model.isSelected());
        adapter.notifyDataSetChanged();
    }

    public void buttonClick(View view) {
//        for(Model m : list) {
//            m.setSelected(!m.isSelected());
//        }
        adapter.groupContentBySelection();
        adapter.notifyDataSetChanged();
    }
}