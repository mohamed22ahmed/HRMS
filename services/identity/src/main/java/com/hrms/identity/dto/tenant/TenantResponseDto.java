package com.hrms.identity.dto.tenant;

import com.hrms.identity.model.Tenant;

import java.util.LinkedHashMap;
import java.util.Map;

public final class TenantResponseDto {
    private TenantResponseDto() {}

    public static Map<String, Object> from(Tenant tenant) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("id", tenant.getId());
        data.put("name", tenant.getName());
        data.put("code", tenant.getCode());
        data.put("slug", tenant.getSlug());
        data.put("email", tenant.getEmail());
        data.put("phone", tenant.getPhone());
        data.put("website", tenant.getWebsite());
        data.put("logo_url", tenant.getLogoUrl());
        data.put("country_code", tenant.getCountryCode());
        data.put("timezone", tenant.getTimezone());
        data.put("status", tenant.getStatus());
        data.put("subscription_plan", tenant.getSubscriptionPlan());
        data.put("trial_ends_at", tenant.getTrialEndsAt());
        data.put("created_at", tenant.getCreatedAt());
        data.put("updated_at", tenant.getUpdatedAt());

        return data;
    }
}
