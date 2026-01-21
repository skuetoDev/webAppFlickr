import { Component, HostListener, Inject, Input, input, output, PLATFORM_ID } from '@angular/core';
import { PhotoCard } from '../photo-card/photo-card';
import { FlickrPhoto } from '../../models/Photo';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-photo-grid',
  imports: [PhotoCard],
  templateUrl: './photo-grid.html',
  styleUrl: './photo-grid.css',
})
export class PhotoGrid {
  photos = input.required<FlickrPhoto[]>();
  loading = input<boolean>(false);
  hasSearched = input<boolean>(false);
  hasMore = input<boolean>(true);
  private isBrowser: boolean;

  onOpenPhoto = output<FlickrPhoto>();
  onLoadMore = output<void>();
  onDeletePhoto = output<string>();

  constructor(@Inject(PLATFORM_ID) platformId: Object) {
    this.isBrowser = isPlatformBrowser(platformId);
  }

  @HostListener('window:scroll')
  onScroll(): void {
    if (!this.isBrowser || this.loading() || !this.hasMore()) return;

    const scrollPosition = window.innerHeight + window.scrollY;
    const threshold = document.documentElement.scrollHeight - 200;

    if (scrollPosition >= threshold) {
      this.onLoadMore.emit();
    }
  }
}
