import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MockComponents, MockService } from 'ng-mocks';
import { MovieWinnerByYearComponent } from '../movie-winner-by-year/movie-winner-by-year.component';
import { MovieService } from './../movies.service';
import { MultipleYearsComponent } from './../multiple-years/multiple-years.component';
import { ProducerMinMaxIntervalComponent } from './../producer-min-max-interval/producer-min-max-interval.component';
import { TopStudiosComponent } from './../top-studios/top-studios.component';
import { DashboardComponent } from './dashboard.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        DashboardComponent,
        MockComponents(
          MultipleYearsComponent,
          TopStudiosComponent,
          ProducerMinMaxIntervalComponent,
          MovieWinnerByYearComponent
        ),
      ],
      providers: [
        { provide: MovieService, useValue: MockService(MovieService) },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
