import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';

interface ApiResponse<T> {
  status: number;
  message: string;
  data: T;
}

interface EmployeeApiDto {
  id: string;
  employee_number: string;
  first_name: string;
  last_name: string;
  email: string;
  phone: string | null;
  department_id: string | null;
  department?: { name: string | null } | null;
  job_title: string | null;
  hire_date: string;
  employment_status: string;
}

interface EmployeeRequest {
  employeeNumber: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  departmentId: string;
  jobTitle: string;
  hireDate: string;
  employmentStatus: string;
}

export interface Employee {
  id: string;
  employeeNumber: string;
  firstName: string;
  lastName: string;
  fullName: string;
  email: string;
  phone: string;
  departmentId: string;
  departmentName: string;
  jobTitle: string;
  hireDate: string;
  employmentStatus: string;
}

export interface EmployeeFormValue {
  employeeNumber: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  departmentId: string;
  jobTitle: string;
  hireDate: string;
  employmentStatus: string;
}

@Injectable({ providedIn: 'root' })
export class EmployeeService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/employees';

  getEmployees(): Observable<Employee[]> {
    return this.http
      .get<ApiResponse<EmployeeApiDto[]>>(this.apiUrl)
      .pipe(map(({ data }) => data.map((employee) => this.toEmployee(employee))));
  }

  createEmployee(employee: EmployeeFormValue): Observable<Employee> {
    return this.http
      .post<ApiResponse<EmployeeApiDto>>(this.apiUrl, this.toRequest(employee))
      .pipe(map(({ data }) => this.toEmployee(data)));
  }

  updateEmployee(id: string, employee: EmployeeFormValue): Observable<Employee> {
    return this.http
      .patch<ApiResponse<EmployeeApiDto>>(`${this.apiUrl}/${id}`, this.toRequest(employee))
      .pipe(map(({ data }) => this.toEmployee(data)));
  }

  deleteEmployee(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  private toEmployee(employee: EmployeeApiDto): Employee {
    return {
      id: employee.id,
      employeeNumber: employee.employee_number,
      firstName: employee.first_name,
      lastName: employee.last_name,
      fullName: `${employee.first_name} ${employee.last_name}`.trim(),
      email: employee.email,
      phone: employee.phone ?? '',
      departmentId: employee.department_id ?? '',
      departmentName: employee.department?.name ?? '',
      jobTitle: employee.job_title ?? '',
      hireDate: employee.hire_date,
      employmentStatus: employee.employment_status,
    };
  }

  private toRequest(employee: EmployeeFormValue): EmployeeRequest {
    return {
      employeeNumber: employee.employeeNumber,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      phone: employee.phone,
      departmentId: employee.departmentId,
      jobTitle: employee.jobTitle,
      hireDate: employee.hireDate,
      employmentStatus: employee.employmentStatus,
    };
  }
}
