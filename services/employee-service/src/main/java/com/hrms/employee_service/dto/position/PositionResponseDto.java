package com.hrms.employee_service.dto.position;

import com.hrms.employee_service.model.Position;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PositionResponseDto {
    private PositionResponseDto() {}

    public static Map<String, Object> from(Position position) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", position.getId());
        data.put("name", position.getName());
        data.put("code", position.getCode());
        data.put("department_id", position.getDepartment() == null ? null : position.getDepartment().getId());
        data.put("is_active", position.getIsActive());
        data.put("created_at", position.getCreatedAt());
        data.put("updated_at", position.getUpdatedAt());
        return data;
    }
}
