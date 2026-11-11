package com.hrms.employee_service.dto.education;

import com.hrms.employee_service.model.Education;

import java.util.LinkedHashMap;
import java.util.Map;

public final class EducationResponseDto {
    private EducationResponseDto() {}

    public static Map<String, Object> from(Education education) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", education.getId());
        data.put("employee_id", education.getEmployee() == null ? null : education.getEmployee().getId());
        data.put("university", education.getUniversity());
        data.put("degree", education.getDegree());
        data.put("department", education.getDepartment());
        data.put("graduate_date", education.getGraduateDate());
        data.put("created_at", education.getCreatedAt());
        data.put("updated_at", education.getUpdatedAt());
        return data;
    }
}
