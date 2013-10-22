package com.example.AndroidTest;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.Arrays;
import java.util.List;

/**
 * User: arne
 * Date: 15.10.13
 * Time: 18:09
 */
public class FakeUrlLoader extends AsyncTaskLoader<List<UrlCategory>> {
    public FakeUrlLoader(Context context) {
        super(context);
    }

    @Override
    public List<UrlCategory> loadInBackground() {
        return Arrays.asList(
            new UrlCategory("course", Arrays.asList(
                new UrlTopic("course Topic 1", Arrays.asList(
                        new UrlEntry("bla bla","www.example.com"),
                        new UrlEntry("blub blub","www.example.com")
                )),
                new UrlTopic("course Topic 2", Arrays.asList(
                        new UrlEntry("Beispiel","www.example.com"),
                        new UrlEntry("Bleistift","www.example.com")
                )),
                new UrlTopic("course Topic 3", Arrays.asList(
                        new UrlEntry("foo","www.example.com"),
                        new UrlEntry("bar","www.example.com")
                ))
            )),
            new UrlCategory("group", Arrays.asList(
                new UrlTopic("group topic 1",Arrays.asList(
                        new UrlEntry("boobar","www.example.com"),
                        new UrlEntry("baz","www.example.com")
                )),
                new UrlTopic("group topic 2",Arrays.asList(
                        new UrlEntry("lolo","www.example.com"),
                        new UrlEntry("Pizza","www.example.com")
                )),
                new UrlTopic("group topic 3",Arrays.asList(
                        new UrlEntry("Wurst","www.example.com"),
                        new UrlEntry("Brot","www.example.com")
                ))
            ))
        );
    }
}
