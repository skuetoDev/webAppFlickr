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
    if (this.searchTerm.trim()) {
      this.search.emit(this.searchTerm.trim());
    }
  }

  onSearchChange(): void {
    // Cancelar búsqueda anterior si existe
    if (this.searchTimeout) {
      clearTimeout(this.searchTimeout);
    }

    // Esperar 500ms después de que el usuario deje de escribir
    this.searchTimeout = setTimeout(() => {
      if (this.searchTerm.trim()) {
        this.search.emit(this.searchTerm.trim());
      }
    }, 500);
  }

  clearSearch(): void {
    this.searchTerm = '';
  }
}
