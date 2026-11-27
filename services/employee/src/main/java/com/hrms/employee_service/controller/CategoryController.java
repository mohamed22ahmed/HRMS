package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.asset.AssetResponseDto;
import com.hrms.employee_service.dto.category.CategoryRequestDto;
import com.hrms.employee_service.dto.category.CategoryResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.model.Category;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.service.CategoryService;
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
@RequestMapping("/api/v1/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Category> categories = categoryService.getCategories();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Categories retrieved successfully", categories, CategoryResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Category category = categoryService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Category retrieved successfully", CategoryResponseDto.from(category)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, CategoryRequestDto.Create.class}) @RequestBody CategoryRequestDto request) {
        Category category = categoryService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Category created successfully", CategoryResponseDto.from(category)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody CategoryRequestDto request) {
        Category category = categoryService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Category updated successfully", CategoryResponseDto.from(category)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Category deleted successfully", Map.of("id", id)));
    }

    @GetMapping("/{id}/assets")
    public ResponseEntity<Map<String, Object>> getAssets(@PathVariable UUID id) {
        List<Asset> assets = categoryService.getAssets(id);
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Category Assets retrieved successfully", assets, AssetResponseDto::from));
    }
}
