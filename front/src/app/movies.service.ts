import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  resource = 'https://tools.texoit.com/backend-java/api/movies';
  constructor(private _httpClient: HttpClient) { }
  dadosDosFilmes(page: number, size: number, winner?: boolean, year?: number) : Observable<Page<Movie>> {

    let queryParams = `?page=${page}&size=${size}`;
    queryParams += typeof winner === 'boolean' ? `&winner=${winner}` : '';
    queryParams += year ? `&year=${year}` : '';
    
    return this._httpClient.get<Page<Movie>>(`${this.resource}${queryParams}`);
  }
  
  anosComMaisDeUmVencedor() : Observable<YearCount[]> {
    
    return this._httpClient.get<{years: YearCount[]}>(`${this.resource}?projection=years-with-multiple-winners`)
    .pipe(map(y => y.years));
  }
  
  estudiosComContadorDeVitoria() : Observable<StudioCount[]> {
    
    return this._httpClient.get<{studios: StudioCount[]}>(`${this.resource}?projection=studios-with-win-count`)
    .pipe(map(y => y.studios));
  }
  
  produtorComMaiorEMenorIntervaloDeVitorias() : Observable<MaxMinIntervalForProducers> {
    
    return this._httpClient.get<MaxMinIntervalForProducers>(`${this.resource}?projection=max-min-win-interval-for-producers`);
  }
}

export interface MaxMinIntervalForProducers {
  min: ProducerWinInterval[]
  max:ProducerWinInterval []
}

export interface ProducerWinInterval {
  producer: string,
  interval: number,
  previousWin: number,
  followingWin: number;
}

export interface StudioCount {
  name: string;
  winCount: number;
}

export interface YearCount {
  year: number;
  winnerCount: number;
}

export interface Page<T> {
  content: T[];
  pageable: Pageable,
  totalElements: number,
  last: boolean,
  totalPages: number,
  fist: boolean,
  sort: Sort,
  number: number,
  numberOfElements: number,
  size: number
}

export interface Pageable {
  sort: Sort,
  pageSize: number,
  pageNumber: number,
  offset: number,
  paged: boolean,
  unpaged: false,
}

export interface Sort{
  sorted: boolean,
  unsorted: boolean
}

export interface Movie {
  id: number;
  year: number;
  title: string;
  studios: string[];
  producers: string[];
  winner: boolean;
}