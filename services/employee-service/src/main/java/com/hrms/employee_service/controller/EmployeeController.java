package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeRequestDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.service.EmployeeService;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/v1/employees")
@CrossOrigin("*")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Employee> employees = employeeService.getEmployees();
        return ResponseEntity.ok(ApiResponseDto.successList(
                200, "Employees retrieved successfully", employees, EmployeeResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Employee employee = employeeService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(
                200, "Employee retrieved successfully", EmployeeResponseDto.from(employee)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(
            @Validated({Default.class, EmployeeRequestDto.Create.class}) @RequestBody EmployeeRequestDto request) {
        Employee employee = employeeService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(
                201, "Employee created successfully", EmployeeResponseDto.from(employee)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable UUID id, @RequestBody EmployeeRequestDto request) {
        Employee employee = employeeService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(
                200, "Employee updated successfully", EmployeeResponseDto.from(employee)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        employeeService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee deleted successfully", Map.of("id", id)));
    }
}
