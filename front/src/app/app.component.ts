import { Component } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front';
  items: MenuItem[] = [
    {label: 'DashBoard', icon: 'pi pi-chart-bar', url: 'dashboard'},
    {label: 'List', icon: 'pi pi-list', url: 'list'},
  ]
}
