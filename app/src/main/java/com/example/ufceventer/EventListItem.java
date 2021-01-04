package com.example.ufceventer;

public class EventListItem {
    private String title;
    private String date;
    private String uri;

    public EventListItem(String title, String date, String uri) {
        this.title = title;
        this.date = date;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getUri() {
        return uri;
    }
}
