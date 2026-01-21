import { Component, EventEmitter, Output, NgZone, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Button } from '../button/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  imports: [Button, CommonModule, FormsModule],
  templateUrl: './search-bar.html',
  styleUrl: './search-bar.css',
})
export class SearchBar implements AfterViewInit {
  searchTerm = '';
  private isReady = false;

  @Output() search = new EventEmitter<string>();

  ngAfterViewInit(): void {
    this.isReady = true;
  }

  doSearch(): void {
    const trimmedTerm = this.searchTerm.trim();
    if (trimmedTerm) {
      this.search.emit(trimmedTerm);
    }
  }

  clearSearch(): void {
    this.searchTerm = '';
  }
}
