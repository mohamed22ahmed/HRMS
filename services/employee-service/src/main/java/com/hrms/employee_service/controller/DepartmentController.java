package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.asset.AssetResponseDto;
import com.hrms.employee_service.dto.branch.BranchResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.department.*;
import com.hrms.employee_service.dto.education.EducationResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.dto.level.LevelResponseDto;
import com.hrms.employee_service.dto.position.PositionResponseDto;
import com.hrms.employee_service.dto.work.WorkResponseDto;
import com.hrms.employee_service.model.*;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.service.DepartmentService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Department> departments = departmentService.getDepartments();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Departments retrieved successfully", departments, DepartmentResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Department department = departmentService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Department retrieved successfully", DepartmentResponseDto.from(department)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, DepartmentRequestDto.Create.class}) @RequestBody DepartmentRequestDto request) {
        Department department = departmentService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Department created successfully", DepartmentResponseDto.from(department)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody DepartmentRequestDto request) {
        Department department = departmentService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Department updated successfully", DepartmentResponseDto.from(department)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {departmentService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Department deleted successfully", Map.of("id", id)));
    }

    @PostMapping("/{id}/set-manager")
    public ResponseEntity<Map<String, Object>> setManager(@PathVariable UUID id ,@RequestBody DepartmentRequestDto request){
        Employee manager = departmentService.setDepartmentManager(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Department manager updated successfully", EmployeeResponseDto.from(manager)));
    }

    @PostMapping("/{id}/change-status")
    public ResponseEntity<Map<String, Object>> changeStatus(@PathVariable UUID id, @RequestBody DepartmentRequestDto request){
        Department dep = departmentService.changeStatus(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Department status changed successfully", DepartmentResponseDto.from(dep)));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Map<String, Object>> getEmployees(@PathVariable UUID id) {
        List<Employee> employees = departmentService.getEmployees(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Department Employees retrieved successfully", employees, EmployeeResponseDto::from));
    }

    @GetMapping("/{id}/positions")
    public ResponseEntity<Map<String, Object>> getPositions(@PathVariable UUID id) {
        List<Position> positions = departmentService.getPositions(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Department Positions retrieved successfully", positions, PositionResponseDto::from));
    }

    @GetMapping("/{id}/levels")
    public ResponseEntity<Map<String, Object>> getLevels(@PathVariable UUID id) {
        List<Level> levels = departmentService.getLevels(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Department Levels retrieved successfully", levels, LevelResponseDto::from));
    }

    @GetMapping("/{id}/works")
    public ResponseEntity<Map<String, Object>> getWorks(@PathVariable UUID id) {
        List<Work> works = departmentService.getWorks(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Department Works retrieved successfully", works, WorkResponseDto::from));
    }

    @GetMapping("/{id}/educations")
    public ResponseEntity<Map<String, Object>> getEducations(@PathVariable UUID id){
        List<Education> educations = departmentService.getEducations(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Department Educations retrieved successfully", educations, EducationResponseDto::from));
    }

    @GetMapping("/{id}/branch")
    public ResponseEntity<Map<String, Object>> getBranch(@PathVariable UUID id){
        Branch branch = departmentService.getBranch(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Department Branch retrieved successfully", BranchResponseDto.from(branch)));
    }

    @GetMapping("/{id}/parent-department")
    public ResponseEntity<Map<String, Object>> getParentDepartment(@PathVariable UUID id){
        Department department = departmentService.getParentDepartment(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Parent Department retrieved successfully", DepartmentResponseDto.from(department)));
    }

    @GetMapping("/{id}/manager")
    public ResponseEntity<Map<String, Object>> getManager(@PathVariable UUID id){
        Employee manager = departmentService.getManager(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Department Manager retrieved successfully", EmployeeResponseDto.from(manager)));
    }
}
