import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FlickrPhoto, FlickrPhotoInfo } from '../models/Photo';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class FlickrService {
  private readonly API_URL = environment.apiUrl;

  constructor(private http: HttpClient) {}

  /**
   * Busca fotos por término con paginación
   * @param query Término de búsqueda
   * @param page Número de página
   * @param perPage Resultados por página
   */
  searchPhotos(query: string, page: number = 1, perPage: number = 20): Observable<FlickrPhoto[]> {
    const params = new HttpParams()
      .set('q', query)
      .set('page', page.toString())
      .set('perPage', perPage.toString());

    return this.http.get<FlickrPhoto[]>(`${this.API_URL}/search`, { params });
  }

  /**
   * Obtiene información detallada de una foto
   * @param id ID de la foto
   */
  getPhotoInfo(id: string): Observable<FlickrPhotoInfo> {
    return this.http.get<FlickrPhotoInfo>(`${this.API_URL}/${id}`);
  }
}
