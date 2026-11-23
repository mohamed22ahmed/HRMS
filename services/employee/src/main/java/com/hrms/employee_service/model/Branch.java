package com.hrms.employee_service.model;

import com.hrms.employee_service.model.asset.Asset;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "branches")
@Getter
@Setter
public class Branch {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "tenant_id")
    private UUID tenantId;

    private String code;
    private String name;
    private String description;
    private String email;
    private String phone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(name = "is_head_office")
    private Boolean isHeadOffice;

    private String status;

    @OneToMany(mappedBy = "branch")
    private List<Department> departments = new ArrayList<>();

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

    public Branch() {}
}
