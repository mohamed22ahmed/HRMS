package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.department.DepartmentEmployeesResponseDto;
import com.hrms.employee_service.dto.position.PositionEmployeesResponseDto;
import com.hrms.employee_service.dto.position.PositionRequestDto;
import com.hrms.employee_service.dto.position.PositionResponseDto;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.model.Position;
import com.hrms.employee_service.service.DepartmentService;
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
@RequestMapping("/api/v1/positions")
@CrossOrigin("*")
public class positionController {
    @Autowired
    private PositionService positionService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Position> positions = positionService.getPositions();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Positions retrieved successfully", positions, PositionResponseDto::from));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Map<String, Object>> getPositionEmployees(@PathVariable UUID id) {
        PositionService.PositionEmployeesResponse data = positionService.getPositionEmployees(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position Employees retrieved successfully", PositionEmployeesResponseDto.from(data)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Position position = positionService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position retrieved successfully", PositionResponseDto.from(position)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, PositionRequestDto.Create.class}) @RequestBody PositionRequestDto request) {
        Position position = positionService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Position created successfully", PositionResponseDto.from(position)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody PositionRequestDto request) {
        Position position = positionService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position updated successfully", PositionResponseDto.from(position)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        positionService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position deleted successfully", Map.of("id", id)));
    }

    @PostMapping("/{id}/reassign-to-department")
    public ResponseEntity<Map<String, Object>>  reassignToDepartment(@PathVariable UUID id,@RequestBody PositionRequestDto request){
        Position position = positionService.reassignToDepartment(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position Assigned to new Department successfully", PositionResponseDto.from(position)));
    }

    @PostMapping("/{id}/drop-from-department")
    public ResponseEntity<Map<String, Object>>  dropFromDepartment(@PathVariable UUID id){
        Position position = positionService.dropFromDepartment(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position Dropped from the Department successfully", PositionResponseDto.from(position)));
    }

    @PostMapping("/{id}/change-status")
    public ResponseEntity<Map<String, Object>> changeStatus(@PathVariable UUID id, @RequestBody PositionRequestDto request){
        Position position = positionService.changeStatus(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Position Status changed successfully", PositionResponseDto.from(position)));
    }
}
