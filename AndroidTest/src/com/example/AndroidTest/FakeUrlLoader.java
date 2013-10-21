package com.example.AndroidTest;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * User: arne
 * Date: 15.10.13
 * Time: 18:09
 */
public class FakeUrlLoader extends AsyncTaskLoader<UrlSet> {
    public FakeUrlLoader(Context context) {
        super(context);
    }

    @Override
    public UrlSet loadInBackground() {
        return new UrlSet(
            new Topic[]{
                new Topic("course Topic 1",
                        new UrlEntry("bla bla","www.example.com"),
                        new UrlEntry("blub blub","www.example.com")
                ),
                new Topic("course Topic 2",
                        new UrlEntry("Beispiel","www.example.com"),
                        new UrlEntry("Bleistift","www.example.com")
                ),
                new Topic("course Topic 3",
                        new UrlEntry("foo","www.example.com"),
                        new UrlEntry("bar","www.example.com")
                )
            },
            new Topic[]{
                new Topic("group topic 1",
                        new UrlEntry("boobar","www.example.com"),
                        new UrlEntry("baz","www.example.com")
                ),
                new Topic("group topic 2",
                        new UrlEntry("lolo","www.example.com"),
                        new UrlEntry("Pizza","www.example.com")
                ),
                new Topic("group topic 3",
                        new UrlEntry("Wurst","www.example.com"),
                        new UrlEntry("Brot","www.example.com")
                )
            }
        );
    }
}
