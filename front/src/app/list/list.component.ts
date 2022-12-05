import { Component, OnInit, ViewChild } from '@angular/core';
import { Paginator } from 'primeng/paginator';
import { Subscription } from 'rxjs';
import { MovieService, Pageable } from '../movies.service';
import { Movie } from './../movies.service';



@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
  winnerOptions = ['Yes','No']
  winner: string = '';
  yearInput: number|null = null;
  movies: Movie[] = [];
  pageable: Pageable = this.defaultPage();
  totalRecords = 0;

  currentRequest!: Subscription;

  @ViewChild(Paginator)
  paginator!: Paginator;

  constructor(private _moviesService: MovieService) {}

  private defaultPage(): Pageable {
    return {
      pageNumber: 0, pageSize: 10
    };
  }

  ngOnInit(): void {
    this.load();
  }

  private load() {

    if (this.currentRequest) {
      this.currentRequest.unsubscribe();
    }
    this.currentRequest = this._moviesService.dadosDosFilmes(
      this.pageable.pageNumber,
      this.pageable.pageSize,
      this.winnerSelected(),
      this.yearInput as any)
      .subscribe(movies => {
        this.movies = movies.content;
        this.pageable = movies.pageable;
        this.totalRecords = movies.totalElements;
      });
  }
  private winnerSelected(): boolean | undefined {
    if (typeof this.winner === 'string' && this.winner.length > 0) {
      return this.winnerOptions.indexOf(this.winner) === 0;
    }
    return undefined;
  }

  dataChange(event?: any) {
    if (event) {
      this.yearInput = event.value;
    }
    this.pageable.pageNumber = 0;
    this.paginator.changePageToFirst({ preventDefault: () => { } });

    this.load();
  }

  pageChange({page,rows}: any) {
    this.pageable = {
      pageNumber: page,
      pageSize: rows
    };
    this.load();
  }
}
