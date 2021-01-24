package com.example.songthrush;

public class Artist {

    String href;
    Items[] items;
    String id;
    String name;
    Images[] images;

    public String getName() {
        return name;
    }

    public Images[] getImages() {
        return images;
    }

    public String getUri() {
        return uri;
    }

    String uri;

    public String getId() {
        return id;
    }

    public String getHref() {
        return href;
    }

    public Items[] getItems() {
        return items;
    }
}
