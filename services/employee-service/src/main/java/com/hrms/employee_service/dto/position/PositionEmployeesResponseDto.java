package com.hrms.employee_service.dto.position;

import com.hrms.employee_service.dto.department.DepartmentResponseDto;
import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.service.DepartmentService;
import com.hrms.employee_service.service.PositionService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class PositionEmployeesResponseDto {
    private PositionEmployeesResponseDto() {}

    public static Map<String, Object> from(PositionService.PositionEmployeesResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("position", PositionResponseDto.from(response.position()));
        data.put("employees", response.employees().stream().map(EmployeeResponseDto::from).toList());
        return data;
    }
}
