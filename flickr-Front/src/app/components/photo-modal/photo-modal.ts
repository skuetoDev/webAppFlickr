import { Component, EventEmitter, HostListener, Input, Output, OnChanges, SimpleChanges, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Button } from "../button/button";
import { FlickrPhotoInfo } from '../../models/Photo';

@Component({
  selector: 'app-photo-modal',
  imports: [CommonModule, Button],
  templateUrl: './photo-modal.html',
  styleUrl: './photo-modal.css',
})
export class PhotoModal implements OnChanges {
  @Input() isOpen = false;
  @Input() photoInfo: FlickrPhotoInfo | null = null;
  @Input() loading = false;

  @Output() onClose = new EventEmitter<void>();

  imageLoaded = false;
  private isBrowser: boolean;

  constructor(@Inject(PLATFORM_ID) platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isOpen'] && this.isBrowser) {
      if (this.isOpen) {
        document.body.style.overflow = 'hidden';
      } else {
        document.body.style.overflow = '';
      }
    }
  }

  @HostListener('document:keydown.escape')
  onEscapeKey(): void {
    if (this.isOpen) {
      this.close();
    }
  }

  close(): void {
    this.imageLoaded = false;
    this.onClose.emit();
  }

  onOverlayClick(event: MouseEvent): void {
    if ((event.target as HTMLElement).classList.contains('modal-overlay')) {
      this.close();
    }
  }

  downloadPhoto(): void {
    if (!this.isBrowser || !this.photoInfo?.id) return;

    // Descargar a traves backend
    window.open(`http://localhost:8080/api/images/${this.photoInfo.id}/download`, '_blank');
  }
}
