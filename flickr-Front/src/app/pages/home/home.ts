import { Component, ChangeDetectorRef } from '@angular/core';
import { Header } from "../../components/header/header";
import { PhotoGrid } from "../../components/photo-grid/photo-grid";
import { FlickrPhoto, FlickrPhotoInfo } from '../../models/Photo';
import { FlickrService } from '../../services/flickr.service';
import { PhotoModal } from "../../components/photo-modal/photo-modal";

@Component({
  selector: 'app-home',
  imports: [Header, PhotoGrid, PhotoModal],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  photos: FlickrPhoto[] = [];
  loading = false;
  hasMore = true;
  hasSearched = false;

  currentQuery = '';
  currentPage = 1;
  readonly perPage = 20;

  // Modal state
  isModalOpen = false;
  selectedPhotoInfo: FlickrPhotoInfo | null = null;
  modalLoading = false;

  constructor(
    private flickrService: FlickrService,
    private cdr: ChangeDetectorRef
  ) {}
  ngOnInit(): void {
    // Búsqueda inicial opcional
    // this.handleSearch('nature');
  }

  handleSearch(query: string): void {
    if (query === this.currentQuery && this.photos.length > 0 && !this.loading) return;

    this.currentQuery = query;
    this.currentPage = 1;
    this.photos = [];
    this.hasMore = true;
    this.hasSearched = true;

    this.loadPhotos();
  }

  loadPhotos(): void {
    if (this.loading || !this.currentQuery) return;

    this.loading = true;

    this.flickrService.searchPhotos(this.currentQuery, this.currentPage, this.perPage).subscribe({
      next: (newPhotos) => {
        this.photos = [...this.photos, ...newPhotos];
        this.hasMore = newPhotos.length === this.perPage;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading photos:', error);
        this.loading = false;
        this.hasMore = false;
      },
    });
  }

  loadMorePhotos(): void {
    if (this.loading || !this.hasMore) return;

    this.currentPage++;
    this.loadPhotos();
  }
  openPhotoModal(photo: FlickrPhoto): void {
    this.isModalOpen = true;
    this.modalLoading = true;
    this.selectedPhotoInfo = null;
    this.cdr.detectChanges();

    this.flickrService.getPhotoInfo(photo.id).subscribe({
      next: (photoInfo) => {
        this.selectedPhotoInfo = photoInfo;
        this.modalLoading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error loading photo info:', error);
        this.modalLoading = false;
        // Crear info básica si falla la carga
        this.selectedPhotoInfo = {
          id: photo.id,
          title: photo.title,
          description: '',
          author: photo.author,
          tags: [],
          imageUrl: photo.imageUrl,
          downloadUrl: photo.imageUrl,
          width: 0,
          height: 0,
        };
        this.cdr.detectChanges();
      },
    });
  }

  closeModal(): void {
    this.isModalOpen = false;
    this.selectedPhotoInfo = null;
  }
}
