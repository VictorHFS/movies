import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TableModule } from 'primeng/table';
import { of } from 'rxjs';
import { Movie, MovieService, Page, Pageable } from '../movies.service';

import { ListComponent } from './list.component';

const MOVIE: Movie = {
  id: 1,
  year: 2020,
  title: 'Title',
  studios: [],
  producers: [],
  winner: true
}
const SORT = { sorted: true, unsorted: false };
const PAGEABLE: Pageable = {
  sort: SORT,
  pageSize: 1,
  pageNumber: 0,
  offset: 0,
  paged: true,
  unpaged: false,
};
const PAGE: Page<Movie> = {
  content: [MOVIE],
  pageable: PAGEABLE,
  totalElements: 0,
  last: false,
  totalPages: 1,
  fist: true,
  sort: SORT,
  number: 0,
  numberOfElements: 1,
  size: 1
};

describe('ListComponent', () => {
  let component: ListComponent;
  let fixture: ComponentFixture<ListComponent>;

  beforeEach(async () => {
    const movieService: jasmine.SpyObj<MovieService> = jasmine.createSpyObj('movieService', ['dadosDosFilmes']);
    movieService.dadosDosFilmes.and.returnValue(of(PAGE))
    
    await TestBed.configureTestingModule({
      imports: [TableModule],
      declarations: [ ListComponent ],
      providers: [
        {provide: MovieService, useValue: movieService}
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
