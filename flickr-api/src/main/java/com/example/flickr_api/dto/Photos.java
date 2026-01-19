package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photos {
    private int page;
    private int pages;

    @JsonProperty("perpage")
    private int perPage;

    private int total;

    @JsonProperty("photo")
    private List<Photo> photoList;

    // Getters y Setters
    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotal() {
        return total;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

}
