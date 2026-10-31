import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Department } from '../../services/department/department';

@Component({
  selector: 'app-department-list',
  standalone: true,
  templateUrl: './department-list.html',
  styleUrl: './department-list.scss',
})
export class DepartmentList {
  @Input() departments: Department[] = [];
  @Input() selectedId: string | null = null;
  @Output() departmentSelected = new EventEmitter<Department>();
}
