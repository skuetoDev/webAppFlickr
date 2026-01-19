package com.example.flickr_api.Services;

import com.example.flickr_api.Models.FlickrPhoto;
import com.example.flickr_api.dto.FlickrResponse;
import com.example.flickr_api.dto.Photo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlickrService {

    @Value("${flickr.api.key}")
    private String apiKey;

    @Value("${flickr.api.url}")
    private String apiUrl;

    // Para búsquedas privadas en un futuro (OAuth)
    @Value("${flickr.api.secret}")
    private String apiSecret;

    private final RestTemplate restTemplate = new RestTemplate();


    /**
     * Busca fotos en Flickr con paginación
     * @param searchTerm término a buscar
     * @param page número de página
     * @param perPage resultados por página
     * @return lista de FlickrPhoto y si no hay fotos o cualquier otro error, un Array vacío
     */
    public List<FlickrPhoto> searchPhotos(String searchTerm, int page, int perPage) {
        // Construir URL con UriComponentsBuilder para mejor manejo de parámetros
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("method", "flickr.photos.search")
                .queryParam("api_key", apiKey)
                .queryParam("text", searchTerm)
                .queryParam("page", page)
                .queryParam("per_page", perPage)
                .queryParam("format", "json")
                .queryParam("nojsoncallback", 1)
                .queryParam("extras", "description,owner_name,tags,url_m,url_l")
                .toUriString();

        System.out.println("URL llamada: " + url);

        try {
            FlickrResponse response = restTemplate.getForObject(url, FlickrResponse.class);

            if (response == null) {
                System.out.println("Response es null");
                return new ArrayList<>();
            }

            System.out.println("Response stat: " + response.getStat());

            if (!"ok".equals(response.getStat())) {
                System.out.println("Flickr devolvió error. Stat: " + response.getStat());
                if (response.getMessage() != null) {
                    System.out.println("Mensaje de error: " + response.getMessage());
                }
                return new ArrayList<>();
            }

            if (response.getPhotos() == null || response.getPhotos().getPhotoList() == null) {
                System.out.println("No se encontraron fotos");
                return new ArrayList<>();
            }

            System.out.println("Fotos encontradas: " + response.getPhotos().getPhotoList().size());
            System.out.println("Total disponible: " + response.getPhotos().getTotal());

            List<FlickrPhoto> resultado = new ArrayList<>();
            for (Photo photo : response.getPhotos().getPhotoList()) {
                resultado.add(convertToFlickrPhoto(photo));
            }
            return resultado;

        } catch (Exception e) {
            System.err.println("Error al buscar fotos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene información detallada de una foto específica
     * @param photoId ID de la foto
     * @return FlickrPhoto con la información o null si no existe
     */
    public FlickrPhoto getPhotoInfo(String photoId) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("method", "flickr.photos.getInfo")
                .queryParam("api_key", apiKey)
                .queryParam("photo_id", photoId)
                .queryParam("format", "json")
                .queryParam("nojsoncallback", 1)
                .toUriString();

        System.out.println("URL getInfo: " + url);

        try {
            // Para getInfo la estructura es diferente, necesitarías otro DTO
            // Por simplicidad, retornamos null aquí
            // Implementar según necesidades específicas
            return null;
        } catch (Exception e) {
            System.err.println("Error al obtener info de foto: " + e.getMessage());
            return null;
        }
    }

    /**
     * Transforma una foto de Flickr al modelo propio
     * @param photo foto de Flickr a transformar
     * @return modelo FlickrPhoto transformado
     */
    private FlickrPhoto convertToFlickrPhoto(Photo photo) {
        // Usar las URLs que Flickr proporciona directamente, o construirlas si no existen
        String imageUrl = photo.getUrlL() != null ? photo.getUrlL() : buildPhotoUrl(photo, "b");
        String thumbnailUrl = photo.getUrlM() != null ? photo.getUrlM() : buildPhotoUrl(photo, "m");
        String author = photo.getOwnerName() != null ? photo.getOwnerName() : photo.getOwner();

        return new FlickrPhoto(
                photo.getId(),
                photo.getTitle(),
                thumbnailUrl,
                imageUrl,
                author
        );
    }

    /**
     * Construye la URL de la foto según el formato de Flickr
     * @param photo foto
     * @param sizeSuffix sufijo de tamaño: s, q, t, m, n, w, z, c, b, h, k (de menor a mayor)
     * @return URL de la foto
     */
    private String buildPhotoUrl(Photo photo, String sizeSuffix) {
        return String.format(
                "https://live.staticflickr.com/%s/%s_%s_%s.jpg",
                photo.getServer(),
                photo.getId(),
                photo.getSecret(),
                sizeSuffix
        );
    }
}
