package com.example.l2plabv0;
import java.util.*;

/**
 * User: arne
 * Date: 17.11.13
 * Time: 15:35
 */
public class StaticData {
    public static List<List<String>> getData() {
        String[] names = new String[]{"group", "topic", "description", "url"};
        ArrayList<List<String>> data = new ArrayList<List<String>>();

        data.add(Arrays.asList("course", "course Topic 1", "bla bla", "www.example.co/*m"));
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

    public static List<Course> getCourseList() {
        return Arrays.asList(
            new Course("languages for scientific computing",
                new Link("Matlab", "http://en.wikipedia.org/wiki/MATLAB"),
                new Link("Mathematica", "http://en.wikipedia.org/wiki/Mathematica"),
                new Link("C programming language", "http://en.wikipedia.org/wiki/C_%28language%29"),
                new Link("numerical analysis", "http://en.wikipedia.org/wiki/Numerical_analysis")
            ),
            new Course("machine learning",
                new Link("Bayes' theorem", "http://en.wikipedia.org/wiki/Bayes%27_theorem"),
                new Link("Singular Value Decomposition", "http://en.wikipedia.org/wiki/Singular_value_decomposition"),
                new Link("Support vector machine","http://en.wikipedia.org/wiki/Support_vector_machine"),
                new Link("k-means clustering", "http://en.wikipedia.org/wiki/K-means_clustering"),
                new Link("Regression analysis", "http://en.wikipedia.org/wiki/Regression_analysis")
            ),
            new Course("computer vision",
               new Link("image segmentation", "http://en.wikipedia.org/wiki/Image_segmentation"),
               new Link("face detection", "http://en.wikipedia.org/wiki/Face_detection"),
               new Link("pedestrian detection", "http://en.wikipedia.org/wiki/Pedestrian_detection")
            )
        );
    }

    public static class Course{
        public String name;
        public List<Link> topics;

        public Course(String name, Link... topics) {
            this.name = name;
            this.topics = new ArrayList<Link>(Arrays.asList(topics));
        }
    }

    public static class Link{
        public String description;
        public String url;
        public String comment;
        public Boolean isBookmarked;
        public String group; // can be empty
        public Date lastModified;

        public Link(String description, String url, String comment, Boolean bookmarked, String group, Date lastModified) {
            this.description = description;
            this.url = url;
            this.comment = comment;
            isBookmarked = bookmarked;
            this.group = group;
            this.lastModified = lastModified;
        }

        public Link(String description, String url) {
            this.description = description;
            this.url = url;
            this.comment = "";
            isBookmarked = false;
            this.group = "";
            this.lastModified = new Date();
        }
    }
}
