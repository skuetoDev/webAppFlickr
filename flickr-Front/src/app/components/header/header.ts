import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchBar } from '../search-bar/search-bar';

@Component({
  selector: 'app-header',
  imports: [CommonModule, SearchBar],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  @Output() search = new EventEmitter<string>();
}
