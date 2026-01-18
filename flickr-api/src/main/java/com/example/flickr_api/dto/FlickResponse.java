package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.codec.binary.StringUtils;

public class FlickResponse {
    @JsonProperty("photos")
    private Photos photos;
    @JsonProperty("stat")
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }

}
