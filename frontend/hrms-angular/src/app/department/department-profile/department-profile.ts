import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { Department } from '../../services/department/department';
import { Employee } from '../../services/employee/employee';

@Component({
  selector: 'app-department-profile',
  standalone: true,
  templateUrl: './department-profile.html',
  styleUrl: './department-profile.scss',
})
export class DepartmentProfile {
  readonly activeTab = signal<'details' | 'employees'>('details');
  @Input({ required: true }) department!: Department;
  @Input() employees: Employee[] = [];
  @Input() unassignedEmployees: Employee[] = [];
  @Input() departments: Department[] = [];
  @Output() edit = new EventEmitter<void>();
  @Output() remove = new EventEmitter<void>();
  @Output() reassign = new EventEmitter<{ employee: Employee; departmentId: string }>();
  @Output() removeEmployee = new EventEmitter<Employee>();
  @Output() addEmployees = new EventEmitter<Employee[]>();

  readonly reassignEmployeeTarget = signal<Employee | null>(null);
  readonly pendingDepartmentId = signal('');
  readonly addEmployeesOpen = signal(false);
  readonly selectedEmployeeIds = signal<Set<string>>(new Set());

  openAddEmployees(): void {
    this.selectedEmployeeIds.set(new Set());
    this.addEmployeesOpen.set(true);
  }

  closeAddEmployees(): void {
    this.addEmployeesOpen.set(false);
    this.selectedEmployeeIds.set(new Set());
  }

  toggleEmployee(employeeId: string): void {
    this.selectedEmployeeIds.update((ids) => {
      const updated = new Set(ids);
      if (updated.has(employeeId)) updated.delete(employeeId);
      else updated.add(employeeId);
      return updated;
    });
  }

  addSelectedEmployees(): void {
    const selected = new Set(this.selectedEmployeeIds());
    const employeesToAdd = this.unassignedEmployees.filter((employee) => selected.has(employee.id));
    if (!employeesToAdd.length) return;
    this.addEmployees.emit(employeesToAdd);
    this.closeAddEmployees();
  }

  openReassign(employee: Employee): void {
    this.reassignEmployeeTarget.set(employee);
    this.pendingDepartmentId.set(employee.departmentId || this.department.id);
  }

  closeReassign(): void {
    this.reassignEmployeeTarget.set(null);
    this.pendingDepartmentId.set('');
  }

  confirmReassign(): void {
    const employee = this.reassignEmployeeTarget();
    const departmentId = this.pendingDepartmentId();
    if (employee && departmentId && departmentId !== employee.departmentId) {
      this.reassign.emit({ employee, departmentId });
      this.closeReassign();
    }
  }
}
