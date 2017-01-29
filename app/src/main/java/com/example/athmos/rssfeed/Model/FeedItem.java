package com.example.athmos.rssfeed.Model;

/**
 * Created by athmos on 27/01/17.
 */

public class FeedItem {
    private String  link;
    private String  description;
    private String  date;
    private String  title;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
