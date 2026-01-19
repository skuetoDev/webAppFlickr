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

    public String getStat() {
        return stat;
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

        public int getCanPrint() {
            return canPrint;
        }

        public int getCanDownload() {
            return canDownload;
        }

        public List<Size> getSizeList() {
            return sizeList;
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

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getSource() {
            return source;
        }

        public String getUrl() {
            return url;
        }

        public String getMedia() {
            return media;
        }

    }
}