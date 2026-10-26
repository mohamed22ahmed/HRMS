import { Component, input, output } from '@angular/core';
import { Employee } from '../../services/employee/employee';

@Component({
  selector: 'app-employee-profile',
  standalone: true,
  templateUrl: './employee-profile.html',
  styleUrl: './employee-profile.scss',
})
export class EmployeeProfile {
  readonly employee = input.required<Employee>();
  readonly edit = output<void>();
  readonly remove = output<void>();

  initials(name: string): string {
    return name.trim().split(/\s+/).slice(0, 2).map((part) => part.charAt(0)).join('').toUpperCase();
  }

  statusLabel(status: string): string {
    return status === 'ON_LEAVE'
      ? 'On leave'
      : status.charAt(0) + status.slice(1).toLowerCase();
  }

}
