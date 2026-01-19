package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * DTO para la respuesta de flickr.photos.getInfo
 * La estructura es diferente a flickr.photos.search
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoInfoResponse {
    private PhotoInfo photo;
    private String stat;

    public PhotoInfo getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoInfo photo) {
        this.photo = photo;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PhotoInfo {
        private String id;
        private String secret;
        private String server;
        private int farm;

        // Dimensiones originales
        @JsonProperty("originalsecret")
        private String originalSecret;

        @JsonProperty("originalformat")
        private String originalFormat;

        private Title title;
        private Description description;
        private Owner owner;
        private Tags tags;
        private Urls urls;

        // Getters y Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public int getFarm() {
            return farm;
        }

        public void setFarm(int farm) {
            this.farm = farm;
        }

        public String getOriginalSecret() {
            return originalSecret;
        }

        public void setOriginalSecret(String originalSecret) {
            this.originalSecret = originalSecret;
        }

        public String getOriginalFormat() {
            return originalFormat;
        }

        public void setOriginalFormat(String originalFormat) {
            this.originalFormat = originalFormat;
        }

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Tags getTags() {
            return tags;
        }

        public void setTags(Tags tags) {
            this.tags = tags;
        }

        public Urls getUrls() {
            return urls;
        }

        public void setUrls(Urls urls) {
            this.urls = urls;
        }

        // Métodos de conveniencia
        public String getTitleContent() {
            return title != null ? title.getContent() : null;
        }

        public String getDescriptionContent() {
            return description != null ? description.getContent() : null;
        }

        public String getOwnerName() {
            return owner != null ? owner.getUsername() : null;
        }

        public List<String> getTagList() {
            if (tags == null || tags.getTagList() == null) {
                return List.of();
            }
            return tags.getTagList().stream()
                    .map(Tag::getRaw)
                    .toList();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {
        @JsonProperty("_content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Description {
        @JsonProperty("_content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Owner {
        private String nsid;
        private String username;
        private String realname;

        public String getNsid() {
            return nsid;
        }

        public void setNsid(String nsid) {
            this.nsid = nsid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tags {
        @JsonProperty("tag")
        private List<Tag> tagList;

        public List<Tag> getTagList() {
            return tagList;
        }

        public void setTagList(List<Tag> tagList) {
            this.tagList = tagList;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tag {
        private String id;
        private String raw;

        @JsonProperty("_content")
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRaw() {
            return raw != null ? raw : content;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Urls {
        @JsonProperty("url")
        private List<Url> urlList;

        public List<Url> getUrlList() {
            return urlList;
        }

        public void setUrlList(List<Url> urlList) {
            this.urlList = urlList;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Url {
        private String type;

        @JsonProperty("_content")
        private String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}