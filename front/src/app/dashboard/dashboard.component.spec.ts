import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { MultipleYearsComponent } from './../multiple-years/multiple-years.component';
import { MockComponent, MockService } from 'ng-mocks';
import { MovieService } from './../movies.service';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ 
        DashboardComponent,
        MockComponent(MultipleYearsComponent)
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
