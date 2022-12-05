import { Component, OnInit } from '@angular/core';
import { MovieService, Pageable } from '../movies.service';
import { Movie } from './../movies.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  movies: Movie[] = [];
  pageable: Pageable = {
    pageNumber: 0, pageSize: 10
  };

  constructor(private _moviesService: MovieService) {}

  ngOnInit(): void {
    this.load();
  }
  private load() {
    this._moviesService.dadosDosFilmes(this.pageable.pageNumber, this.pageable.pageSize)
      .subscribe(movies => {
        this.movies = movies.content;
        this.pageable = movies.pageable;
      });
  }

  pageChange({page,rows}: any) {
    this.pageable = {
      pageNumber: page,
      pageSize: rows
    };
    this.load();
  }
}
