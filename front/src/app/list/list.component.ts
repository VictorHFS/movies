import { Component, OnInit } from '@angular/core';
import { MovieService } from '../movies.service';
import { Movie } from './../movies.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  movies: Movie[] = [];

  constructor(private _moviesService: MovieService) {}

  ngOnInit(): void {
      this._moviesService.dadosDosFilmes(0, 10).subscribe(movies => {
        this.movies = movies.content;
      })
  }
}
