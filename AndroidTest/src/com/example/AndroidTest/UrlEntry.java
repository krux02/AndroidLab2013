package com.example.AndroidTest;

/**
* User: arne
* Date: 15.10.13
* Time: 18:35
*/
public class UrlEntry {
    private String description;
    private String url;

    public UrlEntry(String description, String url) {
        this.description = description;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlEntry{" +
                "description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
