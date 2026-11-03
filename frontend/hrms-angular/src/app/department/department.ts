import { CommonModule } from '@angular/common';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { Department as DepartmentModel, DepartmentFormValue, DepartmentService } from '../services/department/department';
import { DepartmentForm } from './department-form/department-form';
import { DepartmentList } from './department-list/department-list';
import { DepartmentProfile } from './department-profile/department-profile';
import { Employee, EmployeeFormValue, EmployeeService } from '../services/employee/employee';

@Component({
  standalone: true,
  imports: [CommonModule, DepartmentForm, DepartmentList, DepartmentProfile],
  selector: 'app-department',
  styleUrl: './department.scss',
  templateUrl: './department.html',
})
export class Department implements OnInit {
  private readonly departmentService = inject(DepartmentService);
  private readonly employeeService = inject(EmployeeService);

  readonly departments = signal<DepartmentModel[]>([]);
  readonly selectedDepartment = signal<DepartmentModel | null>(null);
  readonly loading = signal(true);
  readonly error = signal('');
  readonly actionError = signal('');
  readonly formOpen = signal(false);
  readonly formDepartment = signal<DepartmentModel | null>(null);
  readonly saving = signal(false);
  readonly deletePromptOpen = signal(false);
  readonly deleting = signal(false);
  readonly employees = signal<Employee[]>([]);
  readonly departmentEmployees = computed(() => {
    const id = this.selectedDepartment()?.id;
    return id ? this.employees().filter((employee) => employee.departmentId === id) : [];
  });
  readonly unassignedEmployees = computed(() => this.employees().filter((employee) => !employee.departmentId));
  readonly employeeToDelete = signal<Employee | null>(null);
  readonly deletingEmployee = signal(false);
  readonly employeeActionError = signal('');
  ngOnInit(): void {
    this.loadDepartments();
    this.employeeService.getEmployees().subscribe({
      next: (employees) => this.employees.set(employees),
      error: () => this.employeeActionError.set('Unable to load department employees.'),
    });
  }

  selectDepartment(department: DepartmentModel): void {
    this.selectedDepartment.set(department);
    this.actionError.set('');
    this.deletePromptOpen.set(false);
  }

  openCreateForm(): void {
    this.formDepartment.set(null);
    this.actionError.set('');
    this.formOpen.set(true);
  }

  openEditForm(): void {
    const department = this.selectedDepartment();
    if (!department) return;
    this.formDepartment.set(department);
    this.actionError.set('');
    this.formOpen.set(true);
  }

  closeForm(): void {
    if (!this.saving()) {
      this.formOpen.set(false);
      this.actionError.set('');
    }
  }

  saveDepartment(form: DepartmentFormValue): void {
    const value: DepartmentFormValue = {
      ...form,
    };
    const existing = this.formDepartment();
    this.saving.set(true);
    this.actionError.set('');
    const request = existing
      ? this.departmentService.updateDepartment(existing.id, value)
      : this.departmentService.createDepartment(value);

    request.subscribe({
      next: (department) => {
        this.departments.update((departments) => {
          const exists = departments.some((item) => item.id === department.id);
          return exists
            ? departments.map((item) => (item.id === department.id ? department : item))
            : [department, ...departments];
        });
        this.selectedDepartment.set(department);
        this.formOpen.set(false);
        this.saving.set(false);
      },
      error: () => {
        this.actionError.set('Unable to save this department. Check the details and try again.');
        this.saving.set(false);
      },
    });
  }

  requestDelete(): void {
    this.actionError.set('');
    this.deletePromptOpen.set(true);
  }

  cancelDelete(): void {
    if (!this.deleting()) this.deletePromptOpen.set(false);
  }

  deleteSelectedDepartment(): void {
    const department = this.selectedDepartment();
    if (!department || this.deleting()) return;
    this.deleting.set(true);
    this.actionError.set('');
    this.departmentService.deleteDepartment(department.id).subscribe({
      next: () => {
        this.departments.update((departments) => departments.filter((item) => item.id !== department.id));
        this.selectedDepartment.set(null);
        this.deletePromptOpen.set(false);
        this.deleting.set(false);
      },
      error: () => {
        this.actionError.set('Unable to delete this department. Please try again.');
        this.deletePromptOpen.set(false);
        this.deleting.set(false);
      },
    });
  }

  reassignEmployee(employee: Employee, departmentId: string): void {
    const formValue: EmployeeFormValue = {
      employeeNumber: employee.employeeNumber,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      phone: employee.phone,
      departmentId,
      jobTitle: employee.jobTitle,
      hireDate: employee.hireDate,
      employmentStatus: employee.employmentStatus,
    };
    this.employeeActionError.set('');
    this.employeeService.updateEmployee(employee.id, formValue).subscribe({
      next: (updated) => this.employees.update((items) => items.map((item) => item.id === updated.id ? updated : item)),
      error: () => this.employeeActionError.set(`Unable to reassign ${employee.fullName}. Please try again.`),
    });
  }

  addEmployeesToDepartment(employees: Employee[], departmentId: string): void {
    employees.forEach((employee) => this.reassignEmployee(employee, departmentId));
  }

  requestEmployeeDelete(employee: Employee): void {
    this.employeeActionError.set('');
    this.employeeToDelete.set(employee);
  }

  deleteEmployee(): void {
    const employee = this.employeeToDelete();
    if (!employee || this.deletingEmployee()) return;
    this.deletingEmployee.set(true);
    this.employeeService.deleteEmployee(employee.id).subscribe({
      next: () => {
        this.employees.update((items) => items.filter((item) => item.id !== employee.id));
        this.employeeToDelete.set(null);
        this.deletingEmployee.set(false);
      },
      error: () => {
        this.employeeActionError.set(`Unable to delete ${employee.fullName}. Please try again.`);
        this.deletingEmployee.set(false);
      },
    });
  }

  private loadDepartments(): void {
    this.departmentService.getDepartments().subscribe({
      next: (departments) => {
        this.departments.set(departments);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Unable to load departments.');
        this.loading.set(false);
      },
    });
  }
}
