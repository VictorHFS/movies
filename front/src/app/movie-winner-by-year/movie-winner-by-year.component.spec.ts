import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { InputNumberModule } from 'primeng/inputnumber';
import { TableModule } from 'primeng/table';
import { of } from 'rxjs';
import { MovieService } from '../movies.service';

import { MovieWinnerByYearComponent } from './movie-winner-by-year.component';

describe('MovieWinnerByYearComponent', () => {
  let component: MovieWinnerByYearComponent;
  let movieService: jasmine.SpyObj<MovieService>;
  let fixture: ComponentFixture<MovieWinnerByYearComponent>;

  beforeEach(async () => {
    movieService = jasmine.createSpyObj('movieService', ['dadosDosFilmes']);
    movieService.dadosDosFilmes.and.returnValue(of());

    await TestBed.configureTestingModule({
      imports: [
        InputNumberModule,
        TableModule,
        FormsModule
      ],
      declarations: [MovieWinnerByYearComponent],
      providers: [
        {provide: MovieService, useValue: movieService}
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MovieWinnerByYearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
