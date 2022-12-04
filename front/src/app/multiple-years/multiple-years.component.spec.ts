import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MovieService } from '../movies.service';

import { MultipleYearsComponent } from './multiple-years.component';
import { MockService } from 'ng-mocks';
import { of } from 'rxjs';
import { TableModule } from 'primeng/table';

describe('MultipleYearsComponent', () => {
  let component: MultipleYearsComponent;
  let movieService;
  let fixture: ComponentFixture<MultipleYearsComponent>;

  beforeEach(async () => {
    movieService = MockService(MovieService, {anosComMaisDeUmVencedor: () => of()});
    await TestBed.configureTestingModule({
      imports: [
        TableModule
      ],
      declarations: [ MultipleYearsComponent ],
      providers: [
       {provide: MovieService, useValue: movieService}
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MultipleYearsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
