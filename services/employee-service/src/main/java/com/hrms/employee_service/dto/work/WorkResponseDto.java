package com.hrms.employee_service.dto.work;

import com.hrms.employee_service.model.Work;

import java.util.LinkedHashMap;
import java.util.Map;

public final class WorkResponseDto {
    private WorkResponseDto() {}

    public static Map<String, Object> from(Work work) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", work.getId());
        data.put("employee_id", work.getEmployee() == null ? null : work.getEmployee().getId());
        data.put("position_id", work.getPosition() == null ? null : work.getPosition().getId());
        data.put("level_id", work.getLevel() == null ? null : work.getLevel().getId());
        data.put("hire_date", work.getHireDate());
        data.put("created_at", work.getCreatedAt());
        data.put("updated_at", work.getUpdatedAt());
        return data;
    }
}
