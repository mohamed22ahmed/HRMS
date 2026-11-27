package com.hrms.identity.dto.tenant;

import com.hrms.identity.dto.common.RequestDto;
import com.hrms.identity.model.Tenant;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class TenantRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private String name;
    private String code;
    private String slug;
    private String email;
    private String phone;
    private String website;
    private String logoUrl;
    private String countryCode;
    private String timezone;
    private String status;
    private String subscriptionPlan;
    private Instant trialEndsAt;

    public Tenant toTenant() {
        Tenant tenant = new Tenant();
        tenant.setName(name);
        tenant.setCode(code);
        tenant.setSlug(slug);
        tenant.setEmail(email);
        tenant.setPhone(phone);
        tenant.setWebsite(website);
        tenant.setLogoUrl(logoUrl);
        tenant.setCountryCode(countryCode);
        tenant.setTimezone(timezone);
        tenant.setStatus(status);
        tenant.setSubscriptionPlan(subscriptionPlan);
        tenant.setTrialEndsAt(trialEndsAt);
        return tenant;
    }
}
