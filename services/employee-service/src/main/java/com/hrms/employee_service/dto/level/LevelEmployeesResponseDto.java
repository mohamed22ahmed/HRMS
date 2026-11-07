package com.hrms.employee_service.dto.level;

import com.hrms.employee_service.dto.department.DepartmentResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.service.DepartmentService;
import com.hrms.employee_service.service.LevelService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LevelEmployeesResponseDto {
    private LevelEmployeesResponseDto() {}

    public static Map<String, Object> from(LevelService.LevelEmployeesResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("level", LevelResponseDto.from(response.level()));
        data.put("employees", response.employees().stream().map(EmployeeResponseDto::from).toList());
        return data;
    }
}
