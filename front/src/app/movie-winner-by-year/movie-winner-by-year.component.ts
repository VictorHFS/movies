import { Component, OnInit } from '@angular/core';
import { Movie, MovieService } from './../movies.service';

@Component({
  selector: 'movie-winner-by-year',
  templateUrl: './movie-winner-by-year.component.html',
  styleUrls: ['./movie-winner-by-year.component.css']
})
export class MovieWinnerByYearComponent implements OnInit {
  yearInput: number = 0;
  movies : Movie[]= []

  constructor(private _movieService: MovieService) {}
  
  ngOnInit() {
    this.loadMovies();
  }

  loadMovies() {
    if (this.yearInput > 0) {
      this._movieService.dadosDosFilmes(0, 5, false, this.yearInput).subscribe(response => {
        this.movies = response.content;
      });
    }
  }
}
