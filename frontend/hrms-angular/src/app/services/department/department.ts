import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';

interface ApiResponse<T> {
  status: number;
  message: string;
  data: T;
}

interface DepartmentApiDto {
  id: string;
  name: string;
  code: string;
  description: string;
  parent_department_id: string | null;
  manager_employee_id: string | null;
  is_active: boolean;
}

interface DepartmentRequest {
  name: string;
  code: string;
  description: string;
  parentDepartmentId: string | null;
  managerEmployeeId: string | null;
  isActive: boolean;
}

export interface Department {
  id: string;
  name: string;
  code: string;
  description: string;
  parent_department_id: string | null;
  manager_employee_id: string | null;
  is_active: boolean;
}

export interface DepartmentFormValue {
  name: string;
  code: string;
  description: string;
  parent_department_id: string | null;
  manager_employee_id: string | null;
  is_active: boolean;
}

@Injectable({ providedIn: 'root' })
export class DepartmentService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/v1/departments';

  getDepartments(): Observable<Department[]> {
    return this.http
      .get<ApiResponse<DepartmentApiDto[]>>(this.apiUrl)
      .pipe(map(({ data }) => data.map((department) => this.toDepartment(department))));
  }

  createDepartment(department: DepartmentFormValue): Observable<Department> {
    return this.http
      .post<ApiResponse<DepartmentApiDto>>(this.apiUrl, this.toRequest(department))
      .pipe(map(({ data }) => this.toDepartment(data)));
  }

  updateDepartment(id: string, department: DepartmentFormValue): Observable<Department> {
    return this.http
      .patch<ApiResponse<DepartmentApiDto>>(`${this.apiUrl}/${id}`, this.toRequest(department))
      .pipe(map(({ data }) => this.toDepartment(data)));
  }

  deleteDepartment(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  private toDepartment(department: DepartmentApiDto): Department {
    return {
      id: department.id,
      name: department.name,
      code: department.code,
      description: department.description,
      parent_department_id: department.parent_department_id ?? null,
      manager_employee_id: department.manager_employee_id ?? null,
      is_active: department.is_active ?? true,
    };
  }

  private toRequest(department: DepartmentFormValue): DepartmentRequest {
    return {
      name: department.name,
      code: department.code,
      description: department.description,
      parentDepartmentId: department.parent_department_id,
      managerEmployeeId: department.manager_employee_id,
      isActive: department.is_active,
    };
  }
}
