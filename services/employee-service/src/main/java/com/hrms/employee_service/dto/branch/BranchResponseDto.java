package com.hrms.employee_service.dto.branch;

import com.hrms.employee_service.model.Branch;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BranchResponseDto {
    private BranchResponseDto() {}

    public static Map<String, Object> from(Branch branch) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", branch.getId());
        data.put("code", branch.getCode());
        data.put("name", branch.getName());
        data.put("description", branch.getDescription());
        data.put("email", branch.getEmail());
        data.put("phone", branch.getPhone());
        data.put("manager_id", branch.getManager().getId());
        data.put("address_id", branch.getAddress().getId());
        data.put("is_head_office", branch.getIsHeadOffice());
        data.put("status", branch.getStatus());
        data.put("created_at", branch.getCreatedAt());
        data.put("updated_at", branch.getUpdatedAt());
        return data;
    }
}
