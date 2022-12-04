import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MovieWinnerByYearComponent } from './movie-winner-by-year.component';

describe('MovieWinnerByYearComponent', () => {
  let component: MovieWinnerByYearComponent;
  let fixture: ComponentFixture<MovieWinnerByYearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MovieWinnerByYearComponent ]
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
