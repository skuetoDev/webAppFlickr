package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo {
    @JsonProperty("id")
    private String id;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("server")
    private String server;
    @JsonProperty("farm")
    private String farm;
    @JsonProperty("title")
    private String title;
    @JsonProperty("ispublic")
    private int isPublic;
    @JsonProperty("isfriend")
    private int isFriend;
    @JsonProperty("isfamily")
    private int isFamily;

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

    public String getFarm() {
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

}
