import { Component, OnInit } from '@angular/core';
import { MaxMinIntervalForProducers, Movie, MovieService, ProducerWinInterval } from './../movies.service';

@Component({
  selector: 'producer-min-max-interval',
  templateUrl: './producer-min-max-interval.component.html',
  styleUrls: ['./producer-min-max-interval.component.css']
})
export class ProducerMinMaxIntervalComponent implements OnInit {
  max: ProducerWinInterval[] = [];
  min: ProducerWinInterval[] = [];

  constructor(private _movieService: MovieService) {}

  ngOnInit(): void {
      this._movieService.produtorComMaiorEMenorIntervaloDeVitorias()
      .subscribe(response => {
        this.max = findGreaterInterval(response);
        this.min = findShorterInterval(response);
      })
  }
}
function findGreaterInterval(response: MaxMinIntervalForProducers) {
  if (response.max.length > 0) {
    const sorted = response.max.sort((a, b) => b.interval - a.interval);
    return [sorted[0]];
  }
  return [];
}
function findShorterInterval(response: MaxMinIntervalForProducers) {
  if (response.min.length > 0) {
    const sorted = response.min.sort((a, b) => a.interval - b.interval);
    return [sorted[0]];
  }
  return [];
}

