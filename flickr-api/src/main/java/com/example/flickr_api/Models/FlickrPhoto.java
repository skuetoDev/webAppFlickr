package com.example.flickr_api.Models;

public class FlickrPhoto {
    private final String id;
    private final String title;
    private final String thumbnailUrl;
    private final String imageUrl;
    private final String author;

    public FlickrPhoto(String id, String title, String thumbnailUrl, String imageUrl, String author) {
        this.id = id;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.author = author;
    }

    // Getters y Setters
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


    @Override
    public String toString() {
        return "FlickrPhoto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}