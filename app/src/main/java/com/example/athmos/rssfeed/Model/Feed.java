package com.example.athmos.rssfeed.Model;

import java.util.List;

/**
 * Created by athmos on 27/01/17.
 */

public class Feed {
    private List<FeedItem>  Feeds;
    private String          url;
    private String          title;
    private String          description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<FeedItem> getFeeds() {
        return Feeds;
    }

    public void setFeeds(List<FeedItem> feeds) {
        Feeds = feeds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
