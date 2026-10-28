package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.department.DepartmentRequestDto;
import com.hrms.employee_service.dto.department.DepartmentResponseDto;
import com.hrms.employee_service.model.Department;
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
}
