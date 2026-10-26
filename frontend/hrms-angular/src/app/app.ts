import { Component } from '@angular/core';
import { Employees } from './employees/employees';
import { Header } from './header/header';

@Component({
  imports: [Header, Employees],
  selector: 'app-root',
  styleUrl: './app.scss',
  templateUrl: './app.html',
})
export class App {}
