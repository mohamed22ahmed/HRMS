package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.asset.AssetResponseDto;
import com.hrms.employee_service.dto.branch.BranchResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.department.DepartmentResponseDto;
import com.hrms.employee_service.dto.education.EducationResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeRequestDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.dto.level.LevelResponseDto;
import com.hrms.employee_service.dto.position.PositionResponseDto;
import com.hrms.employee_service.dto.work.WorkResponseDto;
import com.hrms.employee_service.model.*;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.service.EmployeeService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Employee> employees = employeeService.getEmployees();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Employees retrieved successfully", employees, EmployeeResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Employee employee = employeeService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee retrieved successfully", EmployeeResponseDto.from(employee)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, EmployeeRequestDto.Create.class}) @RequestBody EmployeeRequestDto request) {
        Employee employee = employeeService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Employee created successfully", EmployeeResponseDto.from(employee)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody EmployeeRequestDto request) {
        Employee employee = employeeService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee updated successfully", EmployeeResponseDto.from(employee)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        employeeService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee deleted successfully", Map.of("id", id)));
    }

    @PostMapping("/{id}/reassign-to-department")
    public ResponseEntity<Map<String, Object>>  reassignToDepartment(@PathVariable UUID id,@RequestBody EmployeeRequestDto request){
        Employee emp = employeeService.reassignToDepartment(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Assigned to new Department successfully", EmployeeResponseDto.from(emp)));
    }

    @PostMapping("/{id}/drop-from-department")
    public ResponseEntity<Map<String, Object>>  dropFromDepartment(@PathVariable UUID id){
        Employee emp = employeeService.dropFromDepartment(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Dropped from the Department successfully", EmployeeResponseDto.from(emp)));
    }

    @PostMapping("/{id}/change-status")
    public ResponseEntity<Map<String, Object>> changeStatus(@PathVariable UUID id, @RequestBody EmployeeRequestDto request){
        Employee emp = employeeService.changeStatus(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Status changed successfully", EmployeeResponseDto.from(emp)));
    }

    @GetMapping("/{id}/branch")
    public ResponseEntity<Map<String, Object>> getBranch(@PathVariable UUID id){
        Branch branch = employeeService.getBranch(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Branch retrieved successfully", BranchResponseDto.from(branch)));
    }

    @GetMapping("/{id}/department")
    public ResponseEntity<Map<String, Object>> getDepartment(@PathVariable UUID id){
        Department department = employeeService.getDepartment(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Department retrieved successfully", DepartmentResponseDto.from(department)));
    }

    @GetMapping("/{id}/position")
    public ResponseEntity<Map<String, Object>> getPosition(@PathVariable UUID id){
        Position position = employeeService.getPosition(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Position retrieved successfully", PositionResponseDto.from(position)));
    }

    @GetMapping("/{id}/level")
    public ResponseEntity<Map<String, Object>> getLevel(@PathVariable UUID id){
        Level level = employeeService.getLevel(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Level retrieved successfully", LevelResponseDto.from(level)));
    }

    @GetMapping("/{id}/work")
    public ResponseEntity<Map<String, Object>> getWork(@PathVariable UUID id){
        Work work = employeeService.getWork(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Work retrieved successfully", WorkResponseDto.from(work)));
    }

    @GetMapping("/{id}/education")
    public ResponseEntity<Map<String, Object>> getEducation(@PathVariable UUID id){
        Education education = employeeService.getEducation(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Employee Education retrieved successfully", EducationResponseDto.from(education)));
    }

    @GetMapping("/{id}/assets")
    public ResponseEntity<Map<String, Object>> getAssets(@PathVariable UUID id){
        List<Asset> assets = employeeService.getAssets(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Employee Assets retrieved successfully", assets, AssetResponseDto::from));
    }
}
