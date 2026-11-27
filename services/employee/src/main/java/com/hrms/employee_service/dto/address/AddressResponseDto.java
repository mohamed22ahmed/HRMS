package com.hrms.employee_service.dto.address;

import com.hrms.employee_service.model.Address;

import java.util.LinkedHashMap;
import java.util.Map;

public final class AddressResponseDto {
    private AddressResponseDto() {}

    public static Map<String, Object> from(Address address) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", address.getId());
        data.put("tenant_id", address.getTenantId());
        data.put("country", address.getCountry());
        data.put("state", address.getState());
        data.put("city", address.getCity());
        data.put("location", address.getLocation());
        data.put("postalCode", address.getPostalCode());
        data.put("latitude", address.getLatitude());
        data.put("longitude", address.getLongitude());
        data.put("created_at", address.getCreatedAt());
        data.put("updated_at", address.getUpdatedAt());
        return data;
    }
}
