import { Routes } from '@angular/router';
import { Department } from './department/department';
import { Employees } from './employees/employees';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'employees' },
  { path: 'employees', component: Employees },
  { path: 'department', component: Department },
  { path: 'departments', pathMatch: 'full', redirectTo: 'department' },
  { path: '**', redirectTo: 'employees' },
];
