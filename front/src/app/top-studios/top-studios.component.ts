import { Component, OnInit } from '@angular/core';
import { MovieService, StudioCount } from './../movies.service';

@Component({
  selector: 'top-studios',
  templateUrl: './top-studios.component.html',
  styleUrls: ['./top-studios.component.css']
})
export class TopStudiosComponent implements OnInit {
  
  studios: StudioCount[] = [];

  constructor(private _movieService: MovieService) {}
  
  ngOnInit(): void {
    this._movieService.estudiosComContadorDeVitoria().subscribe(studios => this.studios = studios.slice(0,3));
  }
}
