package com.hrms.employee_service.dto.asset;

import com.hrms.employee_service.model.asset.Asset;

import java.util.LinkedHashMap;
import java.util.Map;

public final class AssetResponseDto {
    private AssetResponseDto() {}

    public static Map<String, Object> from(Asset asset) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", asset.getId());
        data.put("employee_id", asset.getEmployee() == null ? null : asset.getEmployee().getId());
        data.put("name", asset.getName());
        data.put("code", asset.getCode());
        data.put("description", asset.getDescription());
        data.put("category", asset.getCategory());
        data.put("brand", asset.getBrand());
        data.put("model", asset.getModel());
        data.put("serial_number", asset.getSerialNumber());
        data.put("purchase_date", asset.getPurchaseDate());
        data.put("purchase_cost", asset.getPurchaseCost());
        data.put("currency", asset.getCurrency());
        data.put("condition", asset.getCondition());
        data.put("status", asset.getStatus());
        data.put("notes", asset.getNotes());
        data.put("created_at", asset.getCreatedAt());
        data.put("updated_at", asset.getUpdatedAt());
        return data;
    }
}
