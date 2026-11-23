package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.asset.AssetResponseDto;
import com.hrms.employee_service.dto.branch.BranchResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.department.DepartmentResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.dto.level.LevelResponseDto;
import com.hrms.employee_service.dto.position.PositionResponseDto;
import com.hrms.employee_service.dto.work.WorkRequestDto;
import com.hrms.employee_service.dto.work.WorkResponseDto;
import com.hrms.employee_service.model.*;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.service.WorkService;
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
@RequestMapping("/api/v1/works")
@CrossOrigin("*")
public class WorkController {
    @Autowired
    private WorkService workService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Work> works = workService.getWorks();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Works retrieved successfully", works, WorkResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Work work = workService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work retrieved successfully", WorkResponseDto.from(work)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, WorkRequestDto.Create.class}) @RequestBody WorkRequestDto request) {
        Work work = workService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Work created successfully", WorkResponseDto.from(work)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody WorkRequestDto request) {
        Work work = workService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work updated successfully", WorkResponseDto.from(work)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        workService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work deleted successfully", Map.of("id", id)));
    }

    @PostMapping("/{id}/reassign-position")
    public ResponseEntity<Map<String, Object>>  reassignPosition(@PathVariable UUID id,@RequestBody WorkRequestDto request){
        Work work = workService.reassignPosition(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position Assigned to the Work successfully", WorkResponseDto.from(work)));
    }

    @PostMapping("/{id}/drop-position")
    public ResponseEntity<Map<String, Object>>  dropPosition(@PathVariable UUID id){
        Work work = workService.dropPosition(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position dropped from the Work successfully", WorkResponseDto.from(work)));
    }

    @PostMapping("/{id}/reassign-level")
    public ResponseEntity<Map<String, Object>>  reassignLevel(@PathVariable UUID id,@RequestBody WorkRequestDto request){
        Work work = workService.reassignLevel(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level Assigned to the Work successfully", WorkResponseDto.from(work)));
    }

    @PostMapping("/{id}/drop-level")
    public ResponseEntity<Map<String, Object>>  dropLevel(@PathVariable UUID id){
        Work work = workService.dropLevel(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level dropped from the Work successfully", WorkResponseDto.from(work)));
    }

    @GetMapping("/{id}/employee")
    public ResponseEntity<Map<String, Object>> getEmployee(@PathVariable UUID id){
        Employee employee = workService.getEmployee(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work Employee retrieved successfully", EmployeeResponseDto.from(employee)));
    }

    @GetMapping("/{id}/branch")
    public ResponseEntity<Map<String, Object>> getBranch(@PathVariable UUID id){
        Branch branch = workService.getBranch(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work Branch retrieved successfully", BranchResponseDto.from(branch)));
    }

    @GetMapping("/{id}/department")
    public ResponseEntity<Map<String, Object>> getDepartment(@PathVariable UUID id){
        Department department = workService.getDepartment(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work Department retrieved successfully", DepartmentResponseDto.from(department)));
    }

    @GetMapping("/{id}/position")
    public ResponseEntity<Map<String, Object>> getPosition(@PathVariable UUID id){
        Position position = workService.getPosition(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work Position retrieved successfully", PositionResponseDto.from(position)));
    }

    @GetMapping("/{id}/lrvrl")
    public ResponseEntity<Map<String, Object>> getLevel(@PathVariable UUID id){
        Level level = workService.getLevel(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Work Level retrieved successfully", LevelResponseDto.from(level)));
    }
}
