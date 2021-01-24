package com.example.songthrush;

public class Items {
    String id;
    String name;
    String uri;
    String href;
    String album_type;
    Artist[] artists;
    Images[] images;
    Icons[] icons;
    String description;
    Track track;

    public Track getTrack() {
        return track;
    }

    public String getDescription() {
        return description;
    }

    public Icons[] getIcons() {
        return icons;
    }

    public Images[] getImages() {
        return images;
    }

    public String getAlbum_type() {
        return album_type;
    }

    public String getHref() {
        return href;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }
}
