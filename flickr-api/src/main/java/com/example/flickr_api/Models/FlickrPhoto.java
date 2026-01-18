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


    public String getTitle() {
        return title;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public String getAuthor() {
        return author;
    }

}
