import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MenuModule } from 'primeng/menu'
import { TableModule } from 'primeng/table';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ListComponent } from './list/list.component'
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { MultipleYearsComponent } from './multiple-years/multiple-years.component';
import { TopStudiosComponent } from './top-studios/top-studios.component';
import { ProducerMinMaxIntervalComponent } from './producer-min-max-interval/producer-min-max-interval.component';

const routes :Routes = [
  {path: '', component: DashboardComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'list', component: ListComponent},
]

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    ListComponent,
    MultipleYearsComponent,
    TopStudiosComponent,
    ProducerMinMaxIntervalComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes),
    MenuModule,
    TableModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
