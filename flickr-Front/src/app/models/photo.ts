// Modelo para la búsqueda de fotos (respuesta de /search)
export interface FlickrPhoto {
  id: string;
  title: string;
  thumbnailUrl: string;
  imageUrl: string;
  author: string;
}

// Modelo para detalle de foto (respuesta de /{id})
export interface FlickrPhotoInfo {
  id: string;
  title: string;
  description: string;
  author: string;
  tags: string[];
  imageUrl: string;
  downloadUrl: string;
  width: number;
  height: number;
}
