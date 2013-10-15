package com.example.AndroidTest;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * User: arne
 * Date: 15.10.13
 * Time: 14:58
 */
public class StringLoader extends AsyncTaskLoader<String> {
    private static String TAG = "StringLoader";

    public StringLoader(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {
        Log.d(TAG, "loadInBackground");
        try {


            URL url = new URL("http://www.google.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();

            String line = br.readLine();
            for( int i = 0; i < 1000 && line != null; i++ ) {
                sb.append(line).append('\n');
                line = br.readLine();
            }

            String s = sb.toString();

            if(line == null)
                s = "OK "+s;
            else
                s = "NOT"+s;

            Log.d(TAG, s);

            return s;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
