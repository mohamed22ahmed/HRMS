package com.hrms.identity.controller;

import com.hrms.identity.dto.common.ApiResponseDto;
import com.hrms.identity.dto.tenant.TenantRequestDto;
import com.hrms.identity.dto.tenant.TenantResponseDto;
import com.hrms.identity.model.Tenant;
import com.hrms.identity.service.TenantService;
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
@RequestMapping("/api/v1/tenants")
@CrossOrigin("*")
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Tenant> tenants = tenantService.getTenants();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Tenants retrieved successfully", tenants, TenantResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Tenant tenant = tenantService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Tenant retrieved successfully", TenantResponseDto.from(tenant)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, TenantRequestDto.Create.class}) @RequestBody TenantRequestDto request) {
        Tenant tenant = tenantService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Tenant created successfully", TenantResponseDto.from(tenant)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody TenantRequestDto request) {
        Tenant tenant = tenantService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Tenant updated successfully", TenantResponseDto.from(tenant)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        tenantService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Tenant deleted successfully", Map.of("id", id)));
    }
}
