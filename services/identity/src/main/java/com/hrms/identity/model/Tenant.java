package com.hrms.identity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tenants")
@Getter
@Setter
public class Tenant {
    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    private String code;
    private String slug;
    private String email;
    private String phone;
    private String website;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "country_code")
    private String countryCode;

    private String timezone;
    private String status;

    @Column(name = "subscription_plan", nullable = false)
    private String subscriptionPlan;

    @Column(name = "trial_ends_at", nullable = false)
    private Instant trialEndsAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public Tenant() {}
}
