import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { Header } from '../../components/header/header';
import { PhotoGrid } from '../../components/photo-grid/photo-grid';
import { PhotoModal } from '../../photo-modal/photo-modal';
import { FlickrService } from '../../services/flickr.service';
import { FlickrPhoto, FlickrPhotoInfo } from '../../models/Photo';

@Component({
  selector: 'app-home',
  imports: [CommonModule, Header, PhotoGrid, PhotoModal],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit, OnDestroy {
  photos: FlickrPhoto[] = [];
  loading = false;
  hasMore = true;
  hasSearched = false;

  currentQuery = '';
  currentPage = 1;
  readonly perPage = 20;

  // Modal
  isModalOpen = false;
  selectedPhotoInfo: FlickrPhotoInfo | null = null;
  modalLoading = false;

  private photosSubscription?: Subscription;

  constructor(private flickrService: FlickrService) {}

  ngOnInit(): void {
    // Suscribirse a los cambios de fotos
    this.photosSubscription = this.flickrService.photos$.subscribe((photos) => {
      this.photos = photos;
    });
  }

  ngOnDestroy(): void {
    this.photosSubscription?.unsubscribe();
  }

  async handleSearch(query: string): Promise<void> {
     console.log('1. handleSearch called with:', query);
    

    this.currentQuery = query;
    this.currentPage = 1;
    this.hasMore = true;
    this.hasSearched = true;
     console.log('3. Calling loadPhotos');
    await this.loadPhotos(true);
  }

  async loadPhotos(reset: boolean = false): Promise<void> {
    if (this.loading || !this.currentQuery) {
      console.log('5. Skipping - loading or no query');
      return;
    }
    this.loading = true;
    console.log('6. Starting fetch');

    try {
      console.log('4. loadPhotos called, loading:', this.loading, 'query:', this.currentQuery);
      const photos = await this.flickrService.searchPhotos(
        this.currentQuery,
        this.currentPage,
        this.perPage,
        reset,
      );
      console.log('7. Photos received:', photos.length);
      this.hasMore = photos.length >= this.perPage;
    } catch (error) {
      console.error('Error cargando fotos:', error);
      console.error('8. Error:', error);
      this.hasMore = false;
    } finally {
      this.loading = false;
      console.log('9. Loading finished');
    }
  }

  async loadMorePhotos(): Promise<void> {
    if (this.loading || !this.hasMore) return;

    this.currentPage++;
    await this.loadPhotos(false);
  }

  async openPhotoModal(photo: FlickrPhoto): Promise<void> {
    this.isModalOpen = true;
    this.modalLoading = true;
    this.selectedPhotoInfo = null;

    try {
      this.selectedPhotoInfo = await this.flickrService.getPhotoInfo(photo.id);
    } catch (error) {
      console.error('Error cargando info de foto:', error);
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
    } finally {
      this.modalLoading = false;
    }
  }

  closeModal(): void {
    this.isModalOpen = false;
    this.selectedPhotoInfo = null;
  }

  // Método para eliminar foto desde el modal
  deletePhoto(id: string): void {
    this.flickrService.deletePhoto(id);
    this.closeModal();
  }
}
