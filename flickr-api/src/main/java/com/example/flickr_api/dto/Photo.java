package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {
    private String id;
    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;

    @JsonProperty("ispublic")
    private int isPublic;

    @JsonProperty("isfriend")
    private int isFriend;

    @JsonProperty("isfamily")
    private int isFamily;

    // Campos extras que vienen con el parámetro extras
    @JsonProperty("ownername")
    private String ownerName;

    private String tags;

    @JsonProperty("url_m")
    private String urlM;

    @JsonProperty("url_l")
    private String urlL;

    private Description description;

    // Clase interna para la descripción
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Description {
        @JsonProperty("_content")
        private String content;

        public String getContent() {
            return content;
        }

    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getSecret() {
        return secret;
    }


    public String getServer() {
        return server;
    }


    public int getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }


    public int getIsPublic() {
        return isPublic;
    }


    public int getIsFriend() {
        return isFriend;
    }


    public int getIsFamily() {
        return isFamily;
    }


    public String getOwnerName() {
        return ownerName;
    }

    public String getTags() {
        return tags;
    }

    public String getUrlM() {
        return urlM;
    }

    public String getUrlL() {
        return urlL;
    }


    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getDescriptionContent() {
        return description != null ? description.getContent() : null;
    }
}