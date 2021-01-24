package com.example.songthrush;

public class NewRelease {
    String url;
    String id;
    String name;

    public NewRelease(String url, String id, String name) {
        this.url = url;
        this.id = id;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
