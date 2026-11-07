package com.hrms.employee_service.dto.level;

import com.hrms.employee_service.dto.department.DepartmentResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.service.DepartmentService;
import com.hrms.employee_service.service.LevelService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LevelWorksResponseDto {
    private LevelWorksResponseDto() {}

    public static Map<String, Object> from(LevelService.LevelWorksResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("level", LevelResponseDto.from(response.level()));
        data.put("works", response.works().stream().map(WorkResponseDto::from).toList());
        return data;
    }
}
