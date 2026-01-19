package com.example.flickr_api.Models;

import java.util.List;

/**
 * Modelo detallado para información completa de una foto de Flickr.
 * Se usa para el endpoint GET /api/images/{id}
 */
public class FlickrPhotoInfo {
    private final String id;
    private final String title;
    private final String description;
    private final String author;
    private final List<String> tags;
    private final String imageUrl;
    private final String downloadUrl;
    private final int width;
    private final int height;


    // Constructor completo
    public FlickrPhotoInfo(String id, String title, String description, String author,
                           List<String> tags, String imageUrl, String downloadUrl,
                           int width, int height) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.tags = tags;
        this.imageUrl = imageUrl;
        this.downloadUrl = downloadUrl;
        this.width = width;
        this.height = height;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "FlickrPhotoInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                ", imageUrl='" + imageUrl + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}