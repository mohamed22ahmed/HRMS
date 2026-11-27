package com.hrms.employee_service.dto.category;

import com.hrms.employee_service.model.Category;

import java.util.LinkedHashMap;
import java.util.Map;

public final class CategoryResponseDto {
    private CategoryResponseDto() {}

    public static Map<String, Object> from(Category category) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", category.getId());
        data.put("tenant_id", category.getTenantId());
        data.put("name", category.getName());
        data.put("description", category.getDescription());
        data.put("created_at", category.getCreatedAt());
        data.put("updated_at", category.getUpdatedAt());
        return data;
    }
}
