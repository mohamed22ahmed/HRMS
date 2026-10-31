package com.hrms.employee_service.dto.department;

import com.hrms.employee_service.model.Department;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DepartmentResponseDto {
    private DepartmentResponseDto() {}

    public static Map<String, Object> from(Department department) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", department.getId());
        data.put("name", department.getName());
        data.put("code", department.getCode());
        data.put("description", department.getDescription());
        data.put("is_active", department.getIsActive());
        data.put("parent_department_id", department.getParentDepartmentId());
        data.put("manager_employee_id", department.getManagerEmployeeId());
        data.put("created_at", department.getCreatedAt());
        data.put("updated_at", department.getUpdatedAt());
        return data;
    }
}
