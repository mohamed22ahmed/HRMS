package com.hrms.employee_service.dto.department;

import com.hrms.employee_service.dto.position.PositionResponseDto;
import com.hrms.employee_service.service.DepartmentService;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DepartmentPositionsResponseDto {
    private DepartmentPositionsResponseDto() {}

    public static Map<String, Object> from(DepartmentService.DepartmentPositionsResponse response) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("department", DepartmentResponseDto.from(response.department()));
        data.put("positions", response.positions().stream().map(PositionResponseDto::from).toList());
        return data;
    }
}
