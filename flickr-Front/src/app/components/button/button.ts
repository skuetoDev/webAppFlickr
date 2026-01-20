import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';



type ButtonVariant = 'primary' | 'secondary' | 'ghost' | 'danger';
type ButtonSize = 'sm' | 'md' | 'lg';

@Component({
  selector: 'app-button',
  imports: [CommonModule],
  templateUrl: './button.html',
  styleUrl: './button.css',
})
export class Button {
  @Input() variant: ButtonVariant = 'primary';
  @Input() size: ButtonSize = 'md';
  @Input() type: 'button' | 'submit' = 'button';
  @Input() disabled = false;
  @Input() loading = false;
  @Input() fullWidth = false;

  @Output() onClick = new EventEmitter<MouseEvent>();

  get buttonClasses(): string {
    return [`variant-${this.variant}`, `size-${this.size}`, this.fullWidth ? 'full-width' : '']
      .filter(Boolean)
      .join(' ');
  }
}
