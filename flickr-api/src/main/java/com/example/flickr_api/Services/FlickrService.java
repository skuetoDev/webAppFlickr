package com.example.flickr_api.Services;

import com.example.flickr_api.Models.FlickrPhoto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlickrService {
    @Value("${flickr.api.key}")
    private String apiKey;
    @Value("{flickr.api.url}")
    private String apiUrl;
    //para busquedas privadas en un futuro
    @Value("flickr.api.secret")
    private String apiSecret;

    private final RestTemplate restTemplate =new RestTemplate();
    public List<FlickrPhoto> searchPhotos (String searchTerm){
        String url = String.format("%s?method=flickr.photos.search&api_key=%s&text=%s&format=json&nojsoncallback=1&per_page=10",
                apiUrl,apiKey,searchTerm);
        List<FlickrPhoto> photos = new ArrayList<>();
        photos.add (new FlickrPhoto("1","titulo","thumbnail","imgUrl","urlImg"));

        return photos;
    }
}
