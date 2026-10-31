package com.hrms.employee_service.dto.department;

import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.service.DepartmentService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DepartmentEmployeesResponseDto {
    private DepartmentEmployeesResponseDto() {}

    public static Map<String, Object> from(DepartmentService.DepartmentEmployeesResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("department", DepartmentResponseDto.from(response.department()));
        data.put("employees", response.employees().stream().map(EmployeeResponseDto::from).toList());
        return data;
    }
}
