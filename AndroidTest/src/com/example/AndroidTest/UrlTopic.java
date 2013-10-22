package com.example.AndroidTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* User: arne
* Date: 15.10.13
* Time: 18:35
*/
public class UrlTopic {
    private String name;
    private List<UrlEntry> entries;

    public UrlTopic(String name, List<UrlEntry> entries) {
        this.name = name;
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UrlEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<UrlEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "UrlTopic{" +
                "name='" + name + '\'' +
                ", entries=" + entries +
                '}';
    }
}
