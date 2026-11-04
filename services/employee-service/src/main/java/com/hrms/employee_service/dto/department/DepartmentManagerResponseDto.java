package com.hrms.employee_service.dto.department;

import com.hrms.employee_service.dto.employee.EmployeeResponseDto;
import com.hrms.employee_service.service.DepartmentService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DepartmentManagerResponseDto {
    private DepartmentManagerResponseDto() {}

    public static Map<String, Object> from(DepartmentService.DepartmentManagerResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("department_name", response.department_name());
        data.put("employee_name", response.employee_name());
        return data;
    }
}
