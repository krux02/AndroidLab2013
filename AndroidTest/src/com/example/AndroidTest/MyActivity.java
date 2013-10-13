package com.example.AndroidTest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Button button = (Button) findViewById(R.id.internet_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) findViewById(R.id.textView);
                ProgressBar pg = (ProgressBar) findViewById(R.id.progressBar);

                tv.setText("function starts here");

                new Retrieve().execute(tv);
                /*
                InputStream stream = new URL("http://www.google.com").openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                String line = reader.readLine();
                StringBuilder sb = new StringBuilder();
                int maxLines = 5;
                for( int i = 0; i < maxLines && line != null; i++, line = reader.readLine() ) {
                    sb.append(line);
                    sb.append("\\n");
                }
                tv.setText("function ends here");
                */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
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
        System.out.println("Searching...");
    }

    public void openSettings() {
        System.out.println("yo Settings here");
    }


}

class Retrieve extends AsyncTask<TextView, Void, Void> {
    @Override protected Void doInBackground(TextView... textViews) {
        TextView tv = textViews[0];
        try{
            URL url = new URL("http://www.google.com");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = reader.readLine();
            StringBuilder sb = new StringBuilder();
            int maxLines = 5;
            for( int i = 0; i < maxLines && line != null; i++, line = reader.readLine() ) {
                sb.append(line);
                sb.append("\\n");
            }
            tv.setText("function ends here");
        } catch(Exception e){
            tv.setText("Exception: " + e.toString());
        }
        return null;
    }
}
