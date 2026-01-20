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

  @Output() search = new EventEmitter<string>();

  onSubmit(): void {
    if (this.searchTerm.trim()) {
      this.search.emit(this.searchTerm.trim());
    }
  }

  clearSearch(): void {
    this.searchTerm = '';
  }
}
