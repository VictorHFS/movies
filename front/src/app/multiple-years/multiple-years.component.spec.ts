import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MultipleYearsComponent } from './multiple-years.component';

describe('MultipleYearsComponent', () => {
  let component: MultipleYearsComponent;
  let fixture: ComponentFixture<MultipleYearsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MultipleYearsComponent ]
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
