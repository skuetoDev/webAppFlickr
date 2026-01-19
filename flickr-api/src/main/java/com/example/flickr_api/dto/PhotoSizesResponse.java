package com.example.flickr_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * DTO para la respuesta de flickr.photos.getSizes
 * Necesario para obtener width y height de las imágenes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhotoSizesResponse {
    private Sizes sizes;
    private String stat;

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sizes {
        @JsonProperty("canblog")
        private int canBlog;

        @JsonProperty("canprint")
        private int canPrint;

        @JsonProperty("candownload")
        private int canDownload;

        @JsonProperty("size")
        private List<Size> sizeList;

        public int getCanBlog() {
            return canBlog;
        }

        public void setCanBlog(int canBlog) {
            this.canBlog = canBlog;
        }

        public int getCanPrint() {
            return canPrint;
        }

        public void setCanPrint(int canPrint) {
            this.canPrint = canPrint;
        }

        public int getCanDownload() {
            return canDownload;
        }

        public void setCanDownload(int canDownload) {
            this.canDownload = canDownload;
        }

        public List<Size> getSizeList() {
            return sizeList;
        }

        public void setSizeList(List<Size> sizeList) {
            this.sizeList = sizeList;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Size {
        private String label;
        private int width;
        private int height;
        private String source;
        private String url;
        private String media;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }
    }
}