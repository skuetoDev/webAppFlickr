import { Component, Input, input, output } from '@angular/core';
import { PhotoCard } from "../photo-card/photo-card";
import { FlickrPhoto } from '../../models/Photo';

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

  onOpenPhoto = output<FlickrPhoto>();
}
