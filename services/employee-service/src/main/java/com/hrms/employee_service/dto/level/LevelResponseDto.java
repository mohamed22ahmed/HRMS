package com.hrms.employee_service.dto.level;

import com.hrms.employee_service.model.Level;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LevelResponseDto {
    private LevelResponseDto() {}

    public static Map<String, Object> from(Level level) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", level.getId());
        data.put("name", level.getName());
        data.put("code", level.getCode());
        data.put("department_id", level.getDepartment() == null ? null : level.getDepartment().getId());
        data.put("is_active", level.getIsActive());
        data.put("created_at", level.getCreatedAt());
        data.put("updated_at", level.getUpdatedAt());
        return data;
    }
}
