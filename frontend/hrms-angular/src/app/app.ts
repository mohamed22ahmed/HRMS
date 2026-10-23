import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Employees } from './employees/employees';

@Component({
  imports: [Employees],
  selector: 'app-root',
  styleUrl: './app.scss',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('hrms-angular');
}
