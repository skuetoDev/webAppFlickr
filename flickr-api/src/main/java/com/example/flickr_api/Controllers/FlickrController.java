package com.example.flickr_api.Controllers;

import com.example.flickr_api.Models.FlickrPhoto;
import com.example.flickr_api.Services.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "*") // Permitir CORS para desarrollo
public class FlickrController {

    private final FlickrService flickrService;

    @Autowired
    public FlickrController(FlickrService flickrService) {
        this.flickrService = flickrService;
    }

    /**
     * Busca fotos por término
     * GET /api/photos/search?q=cats
     * GET /api/photos/search?q=cats&page=1&perPage=20
     */
    @GetMapping("/search")
    public ResponseEntity<List<FlickrPhoto>> searchPhotos(
            @RequestParam("q") String query,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "10") int perPage) {

        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }

        List<FlickrPhoto> photos = flickrService.searchPhotos(query.trim(), page, perPage);
        return ResponseEntity.ok(photos);
    }

    /**
     * Obtiene información de una foto específica
     * GET /api/photos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<FlickrPhoto> getPhoto(@PathVariable String id) {
        FlickrPhoto photo = flickrService.getPhotoInfo(id);

        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(photo);
    }
}
