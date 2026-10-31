import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Department, DepartmentFormValue } from '../../services/department/department';

@Component({
  selector: 'app-department-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './department-form.html',
  styleUrl: './department-form.scss',
})
export class DepartmentForm implements OnInit {
  @Input() department: Department | null = null;
  @Input() saving = false;
  @Input() error = '';
  @Output() cancelled = new EventEmitter<void>();
  @Output() saved = new EventEmitter<DepartmentFormValue>();

  form: DepartmentFormValue = this.emptyForm();

  ngOnInit(): void {
    if (!this.department) return;
    this.form = {
      name: this.department.name,
      code: this.department.code,
      description: this.department.description,
      parent_department_id: this.department.parent_department_id,
      manager_employee_id: this.department.manager_employee_id,
      is_active: this.department.is_active,
    };
  }

  submit(): void {
    this.saved.emit({
      ...this.form,
      name: this.form.name.trim(),
      code: this.form.code.trim(),
      description: this.form.description.trim(),
      parent_department_id: this.form.parent_department_id?.trim() || null,
      manager_employee_id: this.form.manager_employee_id?.trim() || null,
      is_active: this.form.is_active
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
