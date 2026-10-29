import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Department as DepartmentModel, DepartmentFormValue, DepartmentService } from '../services/department/department';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  selector: 'app-department',
  styleUrl: './department.scss',
  templateUrl: './department.html',
})
export class Department implements OnInit {
  private readonly departmentService = inject(DepartmentService);

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
  form: DepartmentFormValue = this.emptyForm();

  ngOnInit(): void {
    this.loadDepartments();
  }

  selectDepartment(department: DepartmentModel): void {
    this.selectedDepartment.set(department);
    this.actionError.set('');
    this.deletePromptOpen.set(false);
  }

  openCreateForm(): void {
    this.formDepartment.set(null);
    this.form = this.emptyForm();
    this.actionError.set('');
    this.formOpen.set(true);
  }

  openEditForm(): void {
    const department = this.selectedDepartment();
    if (!department) return;
    this.formDepartment.set(department);
    this.form = {
      name: department.name,
      code: department.code,
      description: department.description,
      parent_department_id: department.parent_department_id ?? '',
      manager_employee_id: department.manager_employee_id ?? '',
      is_active: department.is_active,
    };
    this.actionError.set('');
    this.formOpen.set(true);
  }

  closeForm(): void {
    if (!this.saving()) {
      this.formOpen.set(false);
      this.actionError.set('');
    }
  }

  saveDepartment(): void {
    const value: DepartmentFormValue = {
      ...this.form,
      name: this.form.name.trim(),
      code: this.form.code.trim(),
      description: this.form.description.trim(),
      parent_department_id: this.form.parent_department_id?.trim() || null,
      manager_employee_id: this.form.manager_employee_id?.trim() || null,
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

  private emptyForm(): DepartmentFormValue {
    return {
      name: '',
      code: '',
      description: '',
      parent_department_id: null,
      manager_employee_id: null,
      is_active: true,
    };
  }
}
