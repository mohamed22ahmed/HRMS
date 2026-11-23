package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.address.AddressRequestDto;
import com.hrms.employee_service.dto.address.AddressResponseDto;
import com.hrms.employee_service.dto.asset.AssetResponseDto;
import com.hrms.employee_service.dto.branch.BranchRequestDto;
import com.hrms.employee_service.dto.branch.BranchResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.department.DepartmentRequestDto;
import com.hrms.employee_service.dto.department.DepartmentResponseDto;
import com.hrms.employee_service.dto.education.EducationResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.dto.level.LevelResponseDto;
import com.hrms.employee_service.dto.position.PositionResponseDto;
import com.hrms.employee_service.dto.work.WorkResponseDto;
import com.hrms.employee_service.model.*;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.service.BranchService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/branches")
@CrossOrigin("*")
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Branch> branches = branchService.getBranches();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branches retrieved successfully", branches, BranchResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Branch branch = branchService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch retrieved successfully", BranchResponseDto.from(branch)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, BranchRequestDto.Create.class}) @RequestBody BranchRequestDto request) {
        Branch branch = branchService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Branch created successfully", BranchResponseDto.from(branch)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody BranchRequestDto request) {
        Branch branch = branchService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch updated successfully", BranchResponseDto.from(branch)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        branchService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch deleted successfully", Map.of("id", id)));
    }

    @PostMapping("/{id}/set-manager")
    public ResponseEntity<Map<String, Object>> setManager(@PathVariable UUID id ,@RequestBody BranchRequestDto request){
        Employee manager = branchService.setManager(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch manager updated successfully", EmployeeResponseDto.from(manager)));
    }

    @PostMapping("/{id}/change-status")
    public ResponseEntity<Map<String, Object>> changeStatus(@PathVariable UUID id, @RequestBody BranchRequestDto request){
        Branch branch = branchService.changeStatus(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch status changed successfully", BranchResponseDto.from(branch)));
    }

    @PostMapping("/{id}/set-head-office")
    public ResponseEntity<Map<String, Object>> setHeadOffice(@PathVariable UUID id, @RequestBody BranchRequestDto request){
        Branch branch = branchService.setHeadOffice(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch Head Office changed successfully", BranchResponseDto.from(branch)));
    }

    @GetMapping("/{id}/departments")
    public ResponseEntity<Map<String, Object>> getDepartments(@PathVariable UUID id) {
        List<Department> departments = branchService.getDepartments(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branch Departments retrieved successfully", departments, DepartmentResponseDto::from));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Map<String, Object>> getEmployees(@PathVariable UUID id) {
        List<Employee> employees = branchService.getEmployees(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branch Employees retrieved successfully", employees, EmployeeResponseDto::from));
    }

    @GetMapping("/{id}/positions")
    public ResponseEntity<Map<String, Object>> getPositions(@PathVariable UUID id) {
        List<Position> positions = branchService.getPositions(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branch Positions retrieved successfully", positions, PositionResponseDto::from));
    }

    @GetMapping("/{id}/levels")
    public ResponseEntity<Map<String, Object>> getLevels(@PathVariable UUID id) {
        List<Level> levels = branchService.getLevels(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branch Levels retrieved successfully", levels, LevelResponseDto::from));
    }

    @GetMapping("/{id}/works")
    public ResponseEntity<Map<String, Object>> getWorks(@PathVariable UUID id) {
        List<Work> works = branchService.getWorks(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branch Works retrieved successfully", works, WorkResponseDto::from));
    }

    @GetMapping("/{id}/educations")
    public ResponseEntity<Map<String, Object>> getEducations(@PathVariable UUID id){
        List<Education> educations = branchService.getEducations(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branch Educations retrieved successfully", educations, EducationResponseDto::from));
    }

    @GetMapping("/{id}/assets")
    public ResponseEntity<Map<String, Object>> getAssets(@PathVariable UUID id){
        List<Asset> assets = branchService.getAssets(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Branch Assets retrieved successfully", assets, AssetResponseDto::from));
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<Map<String, Object>> getAddress(@PathVariable UUID id) {
        Address address = branchService.getAddress(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch Address retrieved successfully", AddressResponseDto.from(address)));
    }

    @GetMapping("/{id}/manager")
    public ResponseEntity<Map<String, Object>> getManager(@PathVariable UUID id) {
        Employee manager = branchService.getManager(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Branch Manager retrieved successfully", EmployeeResponseDto.from(manager)));
    }
}
