import { Routes } from '@angular/router';
import { Department } from './department/department';
import { Employees } from './employees/employees';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'employees' },
  { path: 'employees', component: Employees },
  { path: 'departments', component: Department },
  { path: '**', redirectTo: 'employees' },
];
