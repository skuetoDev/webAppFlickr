package com.example.flickr_api.Controllers;

import com.example.flickr_api.Models.FlickrPhoto;
import com.example.flickr_api.Services.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/flickr")
public class FlickrController {
    @Autowired
    private FlickrService flickrService;
    @GetMapping("/search")
    public List<FlickrPhoto> searchPhotos (@RequestParam String query) {
        return flickrService.searchPhotos(query);
    }
    @GetMapping("/test")
    public String test() {
        return "API de flickr funcionando correctamente";
    }

}
