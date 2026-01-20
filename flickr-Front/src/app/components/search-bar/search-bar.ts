import { Component, EventEmitter, Output } from '@angular/core';
import { Button } from '../button/button';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  imports: [Button, CommonModule, FormsModule],
  templateUrl: './search-bar.html',
  styleUrl: './search-bar.css',
})
export class SearchBar {
  searchTerm = '';
  private searchTimeout: any;

  @Output() search = new EventEmitter<string>();

  onSubmit(): void {
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
      this.searchTimeout = null;
    }

    const trimmedTerm = this.searchTerm.trim();

    if (trimmedTerm) {
      this.search.emit(trimmedTerm);
    }
  }

  clearSearch(): void {
    this.searchTerm = '';
  }
}
