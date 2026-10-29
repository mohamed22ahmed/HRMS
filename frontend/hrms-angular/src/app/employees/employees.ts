import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Employee, EmployeeFormValue, EmployeeService } from '../services/employee/employee';
import { EmployeeForm } from './employee-form/employee-form';
import { EmployeeList } from './employee-list/employee-list';
import { EmployeeProfile } from './employee-profile/employee-profile';
import { Department } from '../services/department/department';
import { DepartmentService } from '../services/department/department';

@Component({
  selector: 'app-employees',
  standalone: true,
  imports: [CommonModule, EmployeeForm, EmployeeList, EmployeeProfile],
  templateUrl: './employees.html',
  styleUrl: './employees.scss',
})
export class Employees implements OnInit {
  private readonly employeeService = inject(EmployeeService);
  private readonly departmentService = inject(DepartmentService);

  readonly employees = signal<Employee[]>([]);
  readonly departments = signal<Department[]>([]);
  readonly selectedEmployee = signal<Employee | null>(null);
  readonly loading = signal(true);
  readonly error = signal('');
  readonly actionError = signal('');
  readonly formOpen = signal(false);
  readonly formEmployee = signal<Employee | null>(null);
  readonly saving = signal(false);
  readonly deletePromptOpen = signal(false);
  readonly deleting = signal(false);

  ngOnInit(): void {
    this.loadEmployees();
    this.departmentService.getDepartments().subscribe({
      next: (departments) => this.departments.set(departments),
    });
  }

  selectEmployee(employee: Employee): void {
    this.selectedEmployee.set(employee);
    this.actionError.set('');
    this.deletePromptOpen.set(false);
  }

  openCreateForm(): void {
    this.formEmployee.set(null);
    this.actionError.set('');
    this.formOpen.set(true);
  }

  openEditForm(): void {
    this.formEmployee.set(this.selectedEmployee());
    this.actionError.set('');
    this.formOpen.set(true);
  }

  closeForm(): void {
    if (!this.saving()) {
      this.formOpen.set(false);
      this.actionError.set('');
    }
  }

  saveEmployee(formValue: EmployeeFormValue): void {
    const existing = this.formEmployee();
    this.saving.set(true);
    this.actionError.set('');

    const request = existing
      ? this.employeeService.updateEmployee(existing.id, formValue)
      : this.employeeService.createEmployee(formValue);

    request.subscribe({
      next: (employee) => {
        this.employees.update((employees) => {
          const exists = employees.some((item) => item.id === employee.id);
          return exists
            ? employees.map((item) => (item.id === employee.id ? employee : item))
            : [employee, ...employees];
        });
        this.selectedEmployee.set(employee);
        this.formOpen.set(false);
        this.saving.set(false);
      },
      error: () => {
        this.actionError.set('Unable to save this employee. Check the details and try again.');
        this.saving.set(false);
      },
    });
  }

  requestDelete(): void {
    this.actionError.set('');
    this.deletePromptOpen.set(true);
  }

  cancelDelete(): void {
    if (!this.deleting()) {
      this.deletePromptOpen.set(false);
    }
  }

  deleteSelectedEmployee(): void {
    const employee = this.selectedEmployee();
    if (!employee || this.deleting()) return;

    this.deleting.set(true);
    this.actionError.set('');
    this.employeeService.deleteEmployee(employee.id).subscribe({
      next: () => {
        this.employees.update((employees) => employees.filter((item) => item.id !== employee.id));
        this.selectedEmployee.set(null);
        this.deletePromptOpen.set(false);
        this.deleting.set(false);
      },
      error: () => {
        this.actionError.set('Unable to delete this employee. Please try again.');
        this.deletePromptOpen.set(false);
        this.deleting.set(false);
      },
    });
  }

  private loadEmployees(): void {
    this.employeeService.getEmployees().subscribe({
      next: (employees) => {
        this.employees.set(employees);
        this.selectedEmployee.set(null);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Unable to load employees.');
        this.loading.set(false);
      },
    });
  }
}
