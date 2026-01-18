package com.example.flickr_api.Models;

public class FlickrPhoto {
    private String id;
    private String title;
    private String thumbnailUrl;
    private String imageUrl;
    private String author;

    // Constructor empty
    public FlickrPhoto() {}

    // Constructor with params
    public FlickrPhoto(String id, String title, String thumbnailUrl, String imageUrl, String author) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
