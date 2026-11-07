package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.department.DepartmentEmployeesResponseDto;
import com.hrms.employee_service.dto.level.LevelEmployeesResponseDto;
import com.hrms.employee_service.dto.level.LevelRequestDto;
import com.hrms.employee_service.dto.level.LevelResponseDto;
import com.hrms.employee_service.dto.position.PositionRequestDto;
import com.hrms.employee_service.dto.position.PositionResponseDto;
import com.hrms.employee_service.model.Level;
import com.hrms.employee_service.model.Position;
import com.hrms.employee_service.service.DepartmentService;
import com.hrms.employee_service.service.LevelService;
import com.hrms.employee_service.service.PositionService;
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
@RequestMapping("/api/v1/levels")
@CrossOrigin("*")
public class levelController {
    @Autowired
    private LevelService levelService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Level> levels = levelService.getLevels();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Levels retrieved successfully", levels, LevelResponseDto::from));
    }

    @GetMapping("/{id}/works")
    public ResponseEntity<Map<String, Object>> getLevelWorks(@PathVariable UUID id) {
        LevelService.LevelWorksResponse data = levelService.getLevelWorks(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level Works retrieved successfully", LevelWorksResponseDto.from(data)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Level level = levelService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level retrieved successfully", LevelResponseDto.from(level)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, LevelRequestDto.Create.class}) @RequestBody LevelRequestDto request) {
        Level level = levelService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Level created successfully", LevelResponseDto.from(level)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody LevelRequestDto request) {
        Level level = levelService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level updated successfully", LevelResponseDto.from(level)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        levelService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level deleted successfully", Map.of("id", id)));
    }

    @PostMapping("/{id}/change-status")
    public ResponseEntity<Map<String, Object>> changeStatus(@PathVariable UUID id, @RequestBody LevelRequestDto request){
        Level level = levelService.changeStatus(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level Status changed successfully", LevelResponseDto.from(level)));
    }

    @PostMapping("/{id}/reassign-to-department")
    public ResponseEntity<Map<String, Object>>  reassignToDepartment(@PathVariable UUID id,@RequestBody LevelRequestDto request){
        Level level = levelService.reassignToDepartment(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level Assigned to new Department successfully", LevelResponseDto.from(level)));
    }

    @PostMapping("/{id}/drop-from-department")
    public ResponseEntity<Map<String, Object>>  dropFromDepartment(@PathVariable UUID id){
        Level level = levelService.dropFromDepartment(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Level Dropped from the Department successfully", LevelResponseDto.from(level)));
    }
}
