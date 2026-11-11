package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.education.EducationRequestDto;
import com.hrms.employee_service.dto.education.EducationResponseDto;
import com.hrms.employee_service.model.Education;
import com.hrms.employee_service.service.EducationService;
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
@RequestMapping("/api/v1/educations")
@CrossOrigin("*")
public class EducationController {
    @Autowired
    private EducationService educationService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Education> educations = educationService.getEducations();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Educations retrieved successfully", educations, EducationResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Education education = educationService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Education retrieved successfully", EducationResponseDto.from(education)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, EducationRequestDto.Create.class}) @RequestBody EducationRequestDto request) {
        Education education = educationService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Education created successfully", EducationResponseDto.from(education)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody EducationRequestDto request) {
        Education education = educationService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Education updated successfully", EducationResponseDto.from(education)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        educationService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Education deleted successfully", Map.of("id", id)));
    }
}
