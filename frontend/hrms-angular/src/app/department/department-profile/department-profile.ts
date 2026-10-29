import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Department } from '../../services/department/department';

@Component({
  selector: 'app-department-profile',
  standalone: true,
  templateUrl: './department-profile.html',
  styleUrl: './department-profile.scss',
})
export class DepartmentProfile {
  @Input({ required: true }) department!: Department;
  @Output() edit = new EventEmitter<void>();
  @Output() remove = new EventEmitter<void>();
}
