import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Button } from './components/button/button';
import { Header } from './components/header/header';
import { SearchBar } from './components/search-bar/search-bar';
import { PhotoCard } from "./components/photo-card/photo-card";
import { PhotoGrid } from "./components/photo-grid/photo-grid";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, PhotoCard, PhotoGrid],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected readonly title = signal('flickr-Front');
}
