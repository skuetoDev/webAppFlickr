package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlickrResponse {
    private Photos photos;
    private String stat;

    // Para manejo de errores
    private Integer code;
    private String message;

    // Getters y Setters
    public Photos getPhotos() {
        return photos;
    }

    public String getStat() {
        return stat;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
