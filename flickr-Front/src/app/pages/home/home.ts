import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { Header } from '../../components/header/header';
import { PhotoGrid } from '../../components/photo-grid/photo-grid';
import { PhotoModal } from '../../components/photo-modal/photo-modal';
import { FlickrService } from '../../services/flickr.service';
import { FlickrPhoto, FlickrPhotoInfo } from '../../models/Photo';

@Component({
  selector: 'app-home',
  imports: [CommonModule, Header, PhotoGrid, PhotoModal],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit{
  photos: FlickrPhoto[] = [];
  loading = false;
  hasMore = true;
  hasSearched = false;
  private photosSubscription?: Subscription;

  currentQuery = '';
  currentPage = 1;
  readonly perPage = 20;

  // Modal
  isModalOpen = false;
  selectedPhotoInfo: FlickrPhotoInfo | null = null;
  modalLoading = false;


  constructor(
    private flickrService: FlickrService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    // Suscribirse a los cambios de fotos
    this.photosSubscription = this.flickrService.photos$.subscribe((photos) => {
      this.photos = photos;
    });
  }

 

  async handleSearch(query: string): Promise<void> {
    console.log('1. handleSearch called with:', query);

    if (query === this.currentQuery && !this.loading && this.photos.length > 0) {
      return;
    }

    if (this.loading) {
      return;
    }

    this.currentQuery = query;
    this.currentPage = 1;
    this.hasMore = true;
    this.hasSearched = true;

    // Forzar detección de cambios
    this.cdr.detectChanges();

    await this.loadPhotos(true);
  }

  async loadPhotos(reset: boolean = false): Promise<void> {
    if (this.loading || !this.currentQuery) return;

    this.loading = true;
    this.cdr.detectChanges();

    try {
      const photos = await this.flickrService.searchPhotos(
        this.currentQuery,
        this.currentPage,
        this.perPage,
        reset,
      );

      this.hasMore = photos.length >= this.perPage;
    } catch (error) {
      console.error('Error:', error);
      this.hasMore = false;
    } finally {
      this.loading = false;
      this.cdr.detectChanges();
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
    this.cdr.detectChanges();
    

    try {
      this.selectedPhotoInfo = await this.flickrService.getPhotoInfo(photo.id);
      this.cdr.detectChanges();
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
      this.cdr.detectChanges();
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
