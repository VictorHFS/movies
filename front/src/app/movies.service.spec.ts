import { TestBed } from '@angular/core/testing';

import { MovieService } from './movies.service';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';

describe('MoviesService', () => {
  let service: MovieService;
  let httpClient : jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    httpClient = jasmine.createSpyObj('httpClient', ['get']);
    httpClient.get.and.returnValue(of());
    TestBed.configureTestingModule({
      providers: [
        {provide: HttpClient, useValue: httpClient}
      ]
    });
    service = TestBed.inject(MovieService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('dadosDosFilmes with page and size', () => {
    service.dadosDosFilmes(0, 10);
    expect(httpClient.get).toHaveBeenCalledWith('https://tools.texoit.com/backend-java/api/movies?page=0&size=10')
  });

  it('dadosDosFilmes with page, size and winner', () => {
    service.dadosDosFilmes(0, 10, true);
    expect(httpClient.get).toHaveBeenCalledWith('https://tools.texoit.com/backend-java/api/movies?page=0&size=10&winner=true')
  });

  it('dadosDosFilmes with page, size, winner and year', () => {
    service.dadosDosFilmes(0, 10, false, 2020);
    expect(httpClient.get).toHaveBeenCalledWith('https://tools.texoit.com/backend-java/api/movies?page=0&size=10&winner=false&year=2020')
  });

  it('anosComMaisDeUmVencedor', () => {
    service.anosComMaisDeUmVencedor();
    expect(httpClient.get).toHaveBeenCalledWith('https://tools.texoit.com/backend-java/api/movies?projection=years-with-multiple-winners')
  });
});
