package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.address.AddressRequestDto;
import com.hrms.employee_service.dto.address.AddressResponseDto;
import com.hrms.employee_service.dto.branch.BranchRequestDto;
import com.hrms.employee_service.dto.branch.BranchResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.model.Address;
import com.hrms.employee_service.model.Branch;
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
}
