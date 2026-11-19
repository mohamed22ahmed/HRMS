package com.hrms.employee_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    @UuidGenerator
    private UUID id;

    private String country;
    private String state;
    private String city;
    private String location;

    @Column(name = "postal_code")
    private String postalCode;

    private DecimalFormat latitude;
    private DecimalFormat longitude;

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

    public Address() {}
}
