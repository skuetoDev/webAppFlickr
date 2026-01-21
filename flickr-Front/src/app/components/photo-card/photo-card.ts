import { Component, ElementRef, EventEmitter, HostListener, Inject, Input, Output, PLATFORM_ID } from '@angular/core';
import { FlickrPhoto } from '../../models/Photo';
import { Button } from '../button/button';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-photo-card',
  imports: [Button],
  templateUrl: './photo-card.html',
  styleUrl: './photo-card.css',
})
export class PhotoCard {
  @Input({ required: true }) photo!: FlickrPhoto;
  @Output() openPhoto = new EventEmitter<FlickrPhoto>();
  @Output() deletePhoto = new EventEmitter<string>();

  imageLoaded = false;
  isActive = false;
  private isBrowser: boolean;

  constructor(
    @Inject(PLATFORM_ID) platformId: Object,
    private elRef: ElementRef,
  ) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  onImageLoad(): void {
    this.imageLoaded = true;
  }

  onImageError(event: Event): void {
    const img = event.target as HTMLImageElement;
    img.src = 'https://via.placeholder.com/400x300?text=Image+Not+Found';
    this.imageLoaded = true;
  }

  onDeleteClick(): void {
    this.deletePhoto.emit(this.photo.id);
  }

  onOpenClick(): void {
    this.openPhoto.emit(this.photo);
  }
  onCardClick(): void {
    if (this.isBrowser && window.innerWidth <= 768) {
      this.isActive = !this.isActive;
      
    }
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    if (this.isBrowser && this.isActive) {
      const target = event.target as HTMLElement;
      if (!this.elRef.nativeElement.contains(target)) {
        this.isActive = false;
      }
    }
  }
}
