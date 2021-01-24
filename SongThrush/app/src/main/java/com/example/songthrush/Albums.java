package com.example.songthrush;

public class Albums {
    String href;
    Items[] items;
    Artist[] artists;
    String id;
    Images[] images;
    String name;
    String uri;

    public String getId() {
        return id;
    }

    public Images[] getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public String getHref() {
        return href;
    }

    public Items[] getItems() {
        return items;
    }
}
