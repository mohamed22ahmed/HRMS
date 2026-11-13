package com.hrms.employee_service.model.asset;

import com.hrms.employee_service.model.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "assets")
@Getter
@Setter
public class Asset {
    @Id
    @UuidGenerator
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String name;
    private String code;
    private String description;
    private String category;
    private String brand;
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_cost")
    private Double purchaseCost;

    private String currency;

    @Enumerated(EnumType.STRING)
    private Condition condition = Condition.NEW;

    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    private String notes;

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

    public Asset() {}
}
