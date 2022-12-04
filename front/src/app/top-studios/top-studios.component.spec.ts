import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TableModule } from 'primeng/table';
import { of } from 'rxjs';
import { MovieService } from '../movies.service';

import { TopStudiosComponent } from './top-studios.component';

describe('TopStudiosComponent', () => {
  let component: TopStudiosComponent;
  let movieService: jasmine.SpyObj<MovieService>;
  let fixture: ComponentFixture<TopStudiosComponent>;

  beforeEach(async () => {
    movieService = jasmine.createSpyObj('movieService', ['estudiosComContadorDeVitoria']);
    movieService.estudiosComContadorDeVitoria.and.returnValue(of());
    
    await TestBed.configureTestingModule({
      imports: [TableModule],
      declarations: [ TopStudiosComponent ],
      providers: [{provide: MovieService, useValue: movieService}]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopStudiosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
