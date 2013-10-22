package com.example.AndroidTest;

import java.util.*;

/**
 * User: arne
 * Date: 22.10.13
 * Time: 22:45
 */
public class UrlBuilder {
    public static List<UrlCategory> buildCategories(List<List<String>> data) {
        List<UrlCategory> categories = new ArrayList<UrlCategory>();
        while( !data.isEmpty() ) {
            String categoryName = data.get(0).get(0);
            List<List<String>> inCategory = new ArrayList<List<String>>();
            List<List<String>> notInCategory = new ArrayList<List<String>>();
            for(List<String> line : data) {
                if(line.get(0).compareTo(categoryName) == 0)
                    inCategory.add(line);
                else
                    notInCategory.add(line);
            }
            data = notInCategory;
            List<UrlTopic> topics = new ArrayList<UrlTopic>();
            while( !inCategory.isEmpty() ) {
                String topicName = inCategory.get(0).get(1);
                List<List<String>> inTopic = new ArrayList<List<String>>();
                List<List<String>> notInTopic = new ArrayList<List<String>>();
                for(List<String> line : inCategory) {
                    if(line.get(1).compareTo(topicName) == 0)
                        inTopic.add(line);
                    else
                        notInTopic.add(line);
                }
                inCategory = notInTopic;

                List<UrlEntry> urlEntries = new ArrayList<UrlEntry>();
                for(List<String> line : inTopic) {
                    urlEntries.add(new UrlEntry(line.get(2), line.get(3)));
                }

                topics.add(new UrlTopic(topicName, urlEntries));
            }
            categories.add(new UrlCategory(categoryName, topics));
        }
        return categories;
    }
}
