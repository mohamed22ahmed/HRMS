package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.work.WorkRequestDto;
import com.hrms.employee_service.dto.work.WorkResponseDto;
import com.hrms.employee_service.model.Work;
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
}
