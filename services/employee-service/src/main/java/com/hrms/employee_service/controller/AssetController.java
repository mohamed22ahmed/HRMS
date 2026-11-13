package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.asset.AssetRequestDto;
import com.hrms.employee_service.dto.asset.AssetResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.dto.education.EducationRequestDto;
import com.hrms.employee_service.dto.education.EducationResponseDto;
import com.hrms.employee_service.model.Education;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.service.AssetService;
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
@RequestMapping("/api/v1/assets")
@CrossOrigin("*")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Asset> assets = assetService.getAssets();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Assets retrieved successfully", assets, AssetResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Asset asset = assetService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Asset retrieved successfully", AssetResponseDto.from(asset)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, AssetRequestDto.Create.class}) @RequestBody AssetRequestDto request) {
        Asset asset = assetService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Asset created successfully", AssetResponseDto.from(asset)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody AssetRequestDto request) {
        Asset asset = assetService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Asset updated successfully", AssetResponseDto.from(asset)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        assetService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Asset deleted successfully", Map.of("id", id)));
    }

    @PostMapping("/{id}/change-status")
    public ResponseEntity<Map<String, Object>> changeStatus(@PathVariable UUID id, @RequestBody AssetRequestDto request){
        Asset asset = assetService.changeStatus(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Asset status updated successfully", AssetResponseDto.from(asset)));
    }

    @PostMapping("/{id}/change-condition")
    public ResponseEntity<Map<String, Object>> changeCondition(@PathVariable UUID id, @RequestBody AssetRequestDto request){
        Asset asset = assetService.changeCondition(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Asset condition updated successfully", AssetResponseDto.from(asset)));
    }
}
