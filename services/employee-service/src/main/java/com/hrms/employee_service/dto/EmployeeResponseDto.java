package com.hrms.employee_service.dto;

import com.hrms.employee_service.model.Employee;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class EmployeeResponseDto {
    private EmployeeResponseDto() {}

    public static Map<String, Object> success(int status, String message, Employee employee) {
        return successData(status, message, employeeData(employee));
    }

    public static Map<String, Object> successData(int status, String message, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> successList(int status, String message, List<Employee> employees) {
        return successData(status, message, employees.stream().map(EmployeeResponseDto::employeeData).toList());
    }

    public static Map<String, Object> failure(int status, String message, Map<String, String> errors) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("message", message);
        if (errors != null && !errors.isEmpty()) {
            response.put("errors", errors);
        }
        return response;
    }

    private static Map<String, Object> employeeData(Employee employee) {
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
