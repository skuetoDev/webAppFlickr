import { Component, EventEmitter, HostListener, Input, Output, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
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

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['isOpen']) {
      if (this.isOpen) {
        // Bloquear scroll del body
        document.body.style.overflow = 'hidden';
      } else {
        // Restaurar scroll del body
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
    if (this.photoInfo?.downloadUrl) {
      window.open(this.photoInfo.downloadUrl, '_blank');
    }
  }
}
