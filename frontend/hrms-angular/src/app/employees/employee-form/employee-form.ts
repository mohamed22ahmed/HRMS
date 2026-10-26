import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Employee, EmployeeFormValue } from '../../services/employee/employee';

@Component({
  selector: 'app-employee-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './employee-form.html',
  styleUrl: './employee-form.scss',
})
export class EmployeeForm implements OnInit {
  @Input() employee: Employee | null = null;
  @Input() saving = false;
  @Input() error = '';
  @Output() cancelled = new EventEmitter<void>();
  @Output() saved = new EventEmitter<EmployeeFormValue>();

  form: EmployeeFormValue = this.emptyForm();

  ngOnInit(): void {
    if (this.employee) {
      this.form = {
        employeeNumber: this.employee.employeeNumber,
        firstName: this.employee.firstName,
        lastName: this.employee.lastName,
        email: this.employee.email,
        phone: this.employee.phone,
        departmentId: this.employee.departmentId,
        jobTitle: this.employee.jobTitle,
        hireDate: this.employee.hireDate,
        employmentStatus: this.employee.employmentStatus,
      };
    }
  }

  submit(): void {
    this.saved.emit({
      ...this.form,
      employeeNumber: this.form.employeeNumber.trim(),
      firstName: this.form.firstName.trim(),
      lastName: this.form.lastName.trim(),
      email: this.form.email.trim(),
      phone: this.form.phone.trim(),
      departmentId: this.form.departmentId.trim(),
      jobTitle: this.form.jobTitle.trim(),
    });
  }

  private emptyForm(): EmployeeFormValue {
    const localDate = new Date(Date.now() - new Date().getTimezoneOffset() * 60_000)
      .toISOString()
      .slice(0, 10);
    return {
      employeeNumber: '',
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      departmentId: '',
      jobTitle: '',
      hireDate: localDate,
      employmentStatus: 'ACTIVE',
    };
  }
}
