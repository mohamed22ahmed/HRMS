package com.hrms.employee_service.dto.work;

import com.hrms.employee_service.model.Work;

import java.util.LinkedHashMap;
import java.util.Map;

public final class WorkResponseDto {
    private WorkResponseDto() {}

    public static Map<String, Object> from(Work work) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", work.getId());
        data.put("employee_name", work.getEmployee() == null ? null : work.getEmployee().getFirstName() + " " + work.getEmployee().getLastName());
        data.put("position_name", work.getPosition() == null ? null : work.getPosition().getName());
        data.put("level_name", work.getLevel() == null ? null : work.getLevel().getName());
        data.put("hire_date", work.getHireDate());
        data.put("created_at", work.getCreatedAt());
        data.put("updated_at", work.getUpdatedAt());
        return data;
    }
}
