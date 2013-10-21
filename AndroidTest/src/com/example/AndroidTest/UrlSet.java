package com.example.AndroidTest;
import java.util.Arrays;

/**
 * User: arne
 * Date: 15.10.13
 * Time: 18:10
 */
public class UrlSet {
    public Topic[] course;
    public Topic[] group;

    UrlSet(Topic[] course, Topic[] group) {
        this.course = Arrays.copyOf(course, course.length);
        this.group = Arrays.copyOf(group, group.length);
    }

    public Topic[] getCourse() {
        return Arrays.copyOf(course, course.length);
    }

    public void setCourse(Topic[] course) {
        this.course =  Arrays.copyOf(course, course.length);
    }

    public Topic[] getGroup() {
        return Arrays.copyOf(group, group.length);
    }

    public void setGroup(Topic[] group) {
        this.group =  Arrays.copyOf(group, group.length);
    }

    @Override
    public String toString() {
        return "UrlSet{" +
                "course=" + Arrays.toString(course) +
                ", group=" + Arrays.toString(group) +
                '}';
    }
}
