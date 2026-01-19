package com.example.flickr_api.Services;

import com.example.flickr_api.Models.FlickrPhoto;
import com.example.flickr_api.Models.FlickrPhotoInfo;
import com.example.flickr_api.dto.FlickrResponse;
import com.example.flickr_api.dto.Photo;
import com.example.flickr_api.dto.PhotoInfoResponse;
import com.example.flickr_api.dto.PhotoSizesResponse;
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
     * Busca fotos en Flickr según el término de búsqueda
     * @param searchTerm término a buscar
     * @return lista de FlickrPhoto
     */
    public List<FlickrPhoto> searchPhotos(String searchTerm) {
        return searchPhotos(searchTerm, 1, 10);
    }

    /**
     * Busca fotos en Flickr con paginación
     * @param searchTerm término a buscar
     * @param page número de página
     * @param perPage resultados por página
     * @return lista de FlickrPhoto
     */
    public List<FlickrPhoto> searchPhotos(String searchTerm, int page, int perPage) {
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

            return response.getPhotos().getPhotoList().stream()
                    .map(this::convertToFlickrPhoto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error al buscar fotos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene información detallada de una foto específica
     * @param photoId ID de la foto
     * @return FlickrPhotoInfo con la información completa o null si no existe
     */
    public FlickrPhotoInfo getPhotoInfo(String photoId) {
        // 1. Obtener info básica de la foto
        String infoUrl = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("method", "flickr.photos.getInfo")
                .queryParam("api_key", apiKey)
                .queryParam("photo_id", photoId)
                .queryParam("format", "json")
                .queryParam("nojsoncallback", 1)
                .toUriString();

        System.out.println("URL getInfo: " + infoUrl);

        try {
            PhotoInfoResponse infoResponse = restTemplate.getForObject(infoUrl, PhotoInfoResponse.class);

            if (infoResponse == null || !"ok".equals(infoResponse.getStat())) {
                System.out.println("Error obteniendo info de la foto");
                return null;
            }

            if (infoResponse.getPhoto() == null) {
                System.out.println("No se encontró la foto");
                return null;
            }

            // 2. Obtener tamaños de la foto (para width, height y downloadUrl)
            PhotoSizesResponse sizesResponse = getPhotoSizes(photoId);

            // 3. Convertir a FlickrPhotoInfo
            return convertToFlickrPhotoInfo(infoResponse.getPhoto(), sizesResponse);

        } catch (Exception e) {
            System.err.println("Error al obtener info de foto: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene los tamaños disponibles de una foto
     * @param photoId ID de la foto
     * @return PhotoSizesResponse o null si hay error
     */
    private PhotoSizesResponse getPhotoSizes(String photoId) {
        String sizesUrl = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("method", "flickr.photos.getSizes")
                .queryParam("api_key", apiKey)
                .queryParam("photo_id", photoId)
                .queryParam("format", "json")
                .queryParam("nojsoncallback", 1)
                .toUriString();

        System.out.println("URL getSizes: " + sizesUrl);

        try {
            PhotoSizesResponse response = restTemplate.getForObject(sizesUrl, PhotoSizesResponse.class);

            if (response == null || !"ok".equals(response.getStat())) {
                System.out.println("Error obteniendo tamaños de la foto");
                return null;
            }

            return response;
        } catch (Exception e) {
            System.err.println("Error al obtener tamaños: " + e.getMessage());
            return null;
        }
    }


    /**
     * Transforma una foto de Flickr (búsqueda) al modelo propio
     * @param photo foto de Flickr a transformar
     * @return modelo FlickrPhoto transformado
     */
    private FlickrPhoto convertToFlickrPhoto(Photo photo) {
        String imageUrl = photo.getUrlL() != null ? photo.getUrlL() : buildPhotoUrl(photo.getServer(), photo.getId(), photo.getSecret(), "b");
        String thumbnailUrl = photo.getUrlM() != null ? photo.getUrlM() : buildPhotoUrl(photo.getServer(), photo.getId(), photo.getSecret(), "m");
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
     * Transforma la info de Flickr al modelo FlickrPhotoInfo
     * @param photoInfo información de la foto
     * @param sizesResponse respuesta de tamaños (puede ser null)
     * @return FlickrPhotoInfo con todos los datos
     */
    private FlickrPhotoInfo convertToFlickrPhotoInfo(PhotoInfoResponse.PhotoInfo photoInfo, PhotoSizesResponse sizesResponse) {
        // Datos básicos
        String id = photoInfo.getId();
        String title = photoInfo.getTitleContent() != null ? photoInfo.getTitleContent() : "Sin título";
        String description = photoInfo.getDescriptionContent() != null ? photoInfo.getDescriptionContent() : "";
        String author = photoInfo.getOwnerName() != null ? photoInfo.getOwnerName() : "Desconocido";
        List<String> tags = photoInfo.getTagList();

        // URLs e imágenes - valores por defecto
        String imageUrl = buildPhotoUrl(photoInfo.getServer(), photoInfo.getId(), photoInfo.getSecret(), "b");
        String downloadUrl = imageUrl;
        int width = 0;
        int height = 0;

        // Si tenemos los tamaños, buscar el más grande
        if (sizesResponse != null && sizesResponse.getSizes() != null
                && sizesResponse.getSizes().getSizeList() != null) {

            List<PhotoSizesResponse.Size> sizes = sizesResponse.getSizes().getSizeList();

            // Buscar el tamaño "Original" o el más grande disponible
            PhotoSizesResponse.Size largestSize = null;

            for (PhotoSizesResponse.Size size : sizes) {
                // Preferir "Original" si existe
                if ("Original".equals(size.getLabel())) {
                    largestSize = size;
                    break;
                }
                // Si no, quedarse con el más grande por dimensiones
                if (largestSize == null ||
                        (size.getWidth() * size.getHeight()) > (largestSize.getWidth() * largestSize.getHeight())) {
                    largestSize = size;
                }
            }

            if (largestSize != null) {
                imageUrl = largestSize.getSource();
                downloadUrl = largestSize.getSource();
                width = largestSize.getWidth();
                height = largestSize.getHeight();
            }
        }

        return new FlickrPhotoInfo(
                id,
                title,
                description,
                author,
                tags,
                imageUrl,
                downloadUrl,
                width,
                height
        );
    }

    /**
     * Construye la URL de la foto según el formato de Flickr
     * @param server servidor
     * @param id ID de la foto
     * @param secret secreto de la foto
     * @param sizeSuffix sufijo de tamaño: s, q, t, m, n, w, z, c, b, h, k (de menor a mayor)
     * @return URL de la foto
     */
    private String buildPhotoUrl(String server, String id, String secret, String sizeSuffix) {
        return String.format(
                "https://live.staticflickr.com/%s/%s_%s_%s.jpg",
                server,
                id,
                secret,
                sizeSuffix
        );
    }
}
