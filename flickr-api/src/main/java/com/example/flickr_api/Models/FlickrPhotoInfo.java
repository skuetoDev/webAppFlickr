package com.example.flickr_api.Models;

import java.util.List;

/**
 * Modelo detallado para información completa de una foto de Flickr.
 * Se usa para el endpoint GET /api/images/{id}
 */
public class FlickrPhotoInfo {
    private String id;
    private String title;
    private String description;
    private String author;
    private List<String> tags;
    private String imageUrl;
    private String downloadUrl;
    private int width;
    private int height;

    // Constructor vacío
    public FlickrPhotoInfo() {
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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