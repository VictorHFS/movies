import { Component, OnInit } from '@angular/core';
import { MovieService, YearCount } from './../movies.service';

@Component({
  selector: 'multiple-years',
  templateUrl: './multiple-years.component.html',
  styleUrls: ['./multiple-years.component.css']
})
export class MultipleYearsComponent implements OnInit {

  years: YearCount[] = [];

  constructor(private _movieService: MovieService) {}

  ngOnInit(): void {
      this._movieService.anosComMaisDeUmVencedor().subscribe(years => {
        this.years = years;
      });
  }
}
