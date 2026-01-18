package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Photos {
    @JsonProperty("page")
    private int page;
    @JsonProperty("pages")
    private int pages;
    @JsonProperty("perPage")
    private int perPage;
    @JsonProperty("total")
    private int total;
    @JsonProperty("Photo")
    private List<Photo> photoList;

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


}
