package com.example.songthrush;

public class ArtistForRecyclerView {

    String uri;
    String name;
    Images[] images;
    boolean isSelected;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setImages(Images[] images) {
        this.images = images;
    }

    public Images[] getImages() {
        return images;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
