import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { MultipleYearsComponent } from './../multiple-years/multiple-years.component';
import { MockComponent, MockComponents, MockService } from 'ng-mocks';
import { MovieService } from './../movies.service';
import { TopStudiosComponent } from './../top-studios/top-studios.component';
import { ProducerMinMaxIntervalComponent } from './../producer-min-max-interval/producer-min-max-interval.component';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ 
        DashboardComponent,
        MockComponents(MultipleYearsComponent, TopStudiosComponent, ProducerMinMaxIntervalComponent),
     ],
     providers: [
      {provide: MovieService, useValue: MockService(MovieService)}
     ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
