package com.hrms.employee_service.dto.department;

import com.hrms.employee_service.dto.level.LevelResponseDto;
import com.hrms.employee_service.service.DepartmentService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DepartmentLevelsResponseDto {
    private DepartmentLevelsResponseDto() {}

    public static Map<String, Object> from(DepartmentService.DepartmentLevelsResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("department", DepartmentResponseDto.from(response.department()));
        data.put("levels", response.levels().stream().map(LevelResponseDto::from).toList());
        return data;
    }
}
