import { TestBed } from '@angular/core/testing';
import { MenuModule } from 'primeng/menu';
import { AppComponent } from './app.component';
import { MovieService } from './movies.service';

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        MenuModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        {provide: MovieService, useValue: jasmine.createSpyObj('movieService', ['dadosDosFilmes'])}
      ]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });
});
