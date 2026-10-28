package com.hrms.employee_service.dto.employee;

import com.hrms.employee_service.model.Employee;

import java.util.LinkedHashMap;
import java.util.Map;

public final class EmployeeResponseDto {
    private EmployeeResponseDto() {}

    public static Map<String, Object> from(Employee employee) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", employee.getId());
        data.put("employee_number", employee.getEmployeeNumber());
        data.put("first_name", employee.getFirstName());
        data.put("last_name", employee.getLastName());
        data.put("email", employee.getEmail());
        data.put("phone", employee.getPhone());
        data.put("department_id", employee.getDepartmentId());
        data.put("job_title", employee.getJobTitle());
        data.put("hire_date", employee.getHireDate());
        data.put("employment_status", employee.getEmploymentStatus());
        data.put("created_at", employee.getCreatedAt());
        data.put("updated_at", employee.getUpdatedAt());
        return data;
    }
}
