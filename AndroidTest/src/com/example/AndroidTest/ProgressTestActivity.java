package com.example.AndroidTest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * User: arne
 * Date: 12.10.13
 * Time: 14:25
 */
public class ProgressTestActivity extends Activity {
    ProgressBar bar;
    TextView text;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        text = (TextView) findViewById(R.id.textView);
    }

    public void startProgress(View view) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    final int value = i;
                    doFakeWork();
                    bar.post(new Runnable() {
                        @Override
                        public void run() {
                            text.setText("Updating");
                        }
                    });
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    // Simulating something timeconsuming
    private void doFakeWork() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}