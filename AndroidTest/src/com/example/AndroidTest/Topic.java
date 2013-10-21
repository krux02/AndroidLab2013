package com.example.AndroidTest;

import java.util.ArrayList;
import java.util.Arrays;

/**
* User: arne
* Date: 15.10.13
* Time: 18:35
*/
public class Topic {
    private String name;
    private UrlEntry[] entries;

    public Topic(String name, UrlEntry... entries) {
        this.name = name;
        this.entries = Arrays.copyOf(entries,entries.length);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UrlEntry[] getEntries() {
        return Arrays.copyOf(entries, entries.length);
    }

    public void setEntries(UrlEntry[] entries) {
        this.entries = Arrays.copyOf(entries, entries.length);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", entries=" + Arrays.toString(entries) +
                '}';
    }
}
