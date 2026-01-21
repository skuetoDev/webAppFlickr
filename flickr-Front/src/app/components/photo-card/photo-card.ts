import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FlickrPhoto } from '../../models/Photo';
import { Button } from '../button/button';

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
}
