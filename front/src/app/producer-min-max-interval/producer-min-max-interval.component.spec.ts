import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProducerMinMaxIntervalComponent } from './producer-min-max-interval.component';
import { MaxMinIntervalForProducers, MovieService } from './../movies.service';
import { of } from 'rxjs';

const data: MaxMinIntervalForProducers= {"min":[
  {"producer":"Joel Silver","interval":1,"previousWin":1990,"followingWin":1991},
  {"producer":"Joel Silver 2","interval":2,"previousWin":1990,"followingWin":1991},
],"max":[
  {"producer":"Matthew Vaughn","interval":10,"previousWin":2002,"followingWin":2015},
  {"producer":"Matthew Vaughn 2","interval":11,"previousWin":2002,"followingWin":2015},
  ]
};

describe('ProducerMinMaxIntervalComponent', () => {
  let component: ProducerMinMaxIntervalComponent;
  let movieService: jasmine.SpyObj<MovieService>;
  let fixture: ComponentFixture<ProducerMinMaxIntervalComponent>;

  beforeEach(async () => {
   movieService = jasmine.createSpyObj('movieService', ['produtorComMaiorEMenorIntervaloDeVitorias']);
   movieService.produtorComMaiorEMenorIntervaloDeVitorias.and.returnValue(of(data))
    await TestBed.configureTestingModule({
      declarations: [ ProducerMinMaxIntervalComponent ],
      providers: [{provide: MovieService, useValue: movieService}]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProducerMinMaxIntervalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('must load data correctly', () => {
    const max = fixture.componentInstance.max;
    const min = fixture.componentInstance.min;

    expect(max.length).toEqual(1);
    expect(max[0].interval).toEqual(11);
    expect(max[0].producer).toEqual('Matthew Vaughn 2');
    
    expect(min.length).toEqual(1);
    expect(min[0].interval).toEqual(1);
    expect(min[0].producer).toEqual('Joel Silver');
  });
});
