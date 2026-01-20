import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, firstValueFrom } from 'rxjs';
import { FlickrPhoto, FlickrPhotoInfo } from '../models/Photo';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class FlickrService {
  private readonly API_URL = environment.apiUrl;

  // Array local de fotos
  private photos: FlickrPhoto[] = [];

  // IDs de fotos eliminadas (para no mostrarlas de nuevo)
  private deletedIds: Set<string> = new Set();

  // BehaviorSubject para notificar cambios a los componentes
  private photosSubject = new BehaviorSubject<FlickrPhoto[]>([]);
  public photos$ = this.photosSubject.asObservable();

  constructor(private http: HttpClient) {}

  /**
   * Busca fotos por término con paginación
   * @param query Término de búsqueda
   * @param page Número de página
   * @param perPage Resultados por página
   * @param reset Si es true, limpia las fotos anteriores
   */
  async searchPhotos(
    query: string,
    page: number = 1,
    perPage: number = 20,
    reset: boolean = false,
  ): Promise<FlickrPhoto[]> {
    const params = new HttpParams()
      .set('q', query)
      .set('page', page.toString())
      .set('perPage', perPage.toString());

    try {
      const newPhotos = await firstValueFrom(
        this.http.get<FlickrPhoto[]>(`${this.API_URL}/search`, { params }),
      );

      // Filtrar fotos eliminadas
      const filteredPhotos = newPhotos.filter((photo) => !this.deletedIds.has(photo.id));

      if (reset) {
        // Nueva búsqueda: reemplazar fotos
        this.photos = filteredPhotos;
      } else {
        // Cargar más: añadir al array existente
        this.photos = [...this.photos, ...filteredPhotos];
      }

      // Notificar a los suscriptores
      this.photosSubject.next(this.photos);

      return this.photos;
    } catch (error) {
      console.error('Error buscando fotos:', error);
      throw error;
    }
  }

  /**
   * Obtiene información detallada de una foto
   * @param id ID de la foto
   */
  async getPhotoInfo(id: string): Promise<FlickrPhotoInfo> {
    return firstValueFrom(this.http.get<FlickrPhotoInfo>(`${this.API_URL}/${id}`));
  }

  /**
   * Elimina una foto de la vista (no del servidor)
   * @param id ID de la foto a eliminar
   */
  deletePhoto(id: string): void {
    // Añadir a la lista de eliminados
    this.deletedIds.add(id);

    // Eliminar del array local
    this.photos = this.photos.filter((photo) => photo.id !== id);

    // Notificar cambios
    this.photosSubject.next(this.photos);
  }

  /**
   * Restaura todas las fotos eliminadas
   */
  restoreDeletedPhotos(): void {
    this.deletedIds.clear();
  }

  /**
   * Obtiene el array actual de fotos
   */
  getPhotos(): FlickrPhoto[] {
    return this.photos;
  }

  /**
   * Limpia todas las fotos
   */
  clearPhotos(): void {
    this.photos = [];
    this.photosSubject.next(this.photos);
  }
}
