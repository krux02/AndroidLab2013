package com.example.AndroidTest;
import java.util.Arrays;
import java.util.List;

/**
 * User: arne
 * Date: 15.10.13
 * Time: 18:10
 */
public class UrlCategory {
    public String name;
    public List<UrlTopic> course;

    public UrlCategory(String name, List<UrlTopic> course) {
        this.name = name;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UrlTopic> getCourse() {
        return course;
    }

    public void setCourse(List<UrlTopic> course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "UrlCategory{" +
                "name='" + name + '\'' +
                ", course=" + course +
                '}';
    }
}
