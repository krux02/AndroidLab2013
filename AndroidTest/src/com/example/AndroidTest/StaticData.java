package com.example.AndroidTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: arne
 * Date: 17.11.13
 * Time: 15:35
 */
public class StaticData {
    public static List<List<String>> getData() {
        String[] names = new String[]{"group", "topic", "description", "url"};
        ArrayList<List<String>> data = new ArrayList<List<String>>();
        data.add(Arrays.asList("course", "course Topic 1", "bla bla", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 1", "blub blub", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 2", "Beispiel", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 2", "Bleistift", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 3", "foo", "www.example.com"));
        data.add(Arrays.asList("course", "course Topic 3", "bar", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 1", "foobar", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 1", "baz", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 2", "lolo", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 2", "Pizza", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 3", "Wurst", "www.example.com"));
        data.add(Arrays.asList("group", "group Topic 3", "Brot", "www.example.com"));
        return data;
    }

    public static List<Group> getGroups() {
        return Arrays.asList(
            new Group("course",
                new Topic("course topic 1",
                    new Link("bla bla", "www.example.com"),
                    new Link("blub blub", "www.example.com")
                ),
                new Topic("course topic 2",
                    new Link("Beispiel", "www.example.com"),
                    new Link("Bleistift", "www.example.com")
                ),
                new Topic("course topic 3",
                    new Link("foo", "www.example.com"),
                    new Link("bar", "www.example.com")
                )
            ),
            new Group("group",
                new Topic("group topic 1",
                    new Link("foobar", "www.example.com"),
                    new Link("baz", "www.example.com")
                ),
                new Topic("group topic 2",
                    new Link("lolo", "www.example.com"),
                    new Link("Pizza", "www.example.com")
                ),
                new Topic("group topic 3",
                    new Link("Wurst", "www.example.com"),
                    new Link("Brot", "www.example.com")
                )
            )
        );
    }

    public static class Group{
        public String name;
        public List<Topic> topics;

        public Group(String name, Topic... topics) {
            this.name = name;
            this.topics = Arrays.asList(topics);
        }
    }

    public static class Topic{
        public String name;
        public List<Link> links;

        public Topic(String name, Link... links) {
            this.name = name;
            this.links = Arrays.asList(links);
        }
    }

    public static class Link{
        public String description;
        public String url;

        public Link(String description, String url) {
            this.description = description;
            this.url = url;
        }
    }
}
