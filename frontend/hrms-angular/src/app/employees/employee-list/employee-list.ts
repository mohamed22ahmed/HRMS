import { Component, input, output } from '@angular/core';
import { Employee } from '../../services/employee/employee';

@Component({
  selector: 'app-employee-list',
  standalone: true,
  templateUrl: './employee-list.html',
  styleUrl: './employee-list.scss',
})
export class EmployeeList {
  readonly employees = input.required<Employee[]>();
  readonly selectedId = input<string | null>(null);
  readonly employeeSelected = output<Employee>();
  initials(name: string): string {
    return name
      .trim()
      .split(/\s+/)
      .slice(0, 2)
      .map((part) => part.charAt(0))
      .join('')
      .toUpperCase();
  }

  statusLabel(status: string): string {
    return status === 'ON_LEAVE'
      ? 'On leave'
      : status.charAt(0) + status.slice(1).toLowerCase();
  }
}
