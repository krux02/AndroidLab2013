package com.example.l2plabv0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * User: arne
 * Date: 17.12.13
 * Time: 13:34
 */
public class LinkFormActivity extends Activity {
    public static String TAG = "LinkFormActivity";

    EditText nameView, urlView, descriptionView;
    CheckBox bookmarked;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Create");

        setContentView(R.layout.activity_new_link_form);
        nameView        = (EditText)findViewById(R.id.name);
        urlView         = (EditText)findViewById(R.id.url);
        descriptionView = (EditText)findViewById(R.id.description);
        bookmarked      = (CheckBox)findViewById(R.id.bookmark);
    }

    public void onOk(View view) {
        Log.d(TAG, "OK pressed");
        Intent returnIntent = getIntent();

        StaticData.Link link = new StaticData.Link(
                descriptionView.getText().toString(),
                urlView.getText().toString(),
                nameView.getText().toString(),
                bookmarked.isChecked(),
                "",
                Calendar.getInstance().getTime()
        );

        returnIntent.putExtra("link", link);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void onCancel(View view) {
        Log.d(TAG, "cancel pressed");
        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED,returnIntent);
        finish();
    }
}
