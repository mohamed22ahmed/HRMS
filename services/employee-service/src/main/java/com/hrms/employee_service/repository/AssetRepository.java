package com.hrms.employee_service.repository;

import com.hrms.employee_service.model.asset.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssetRepository extends JpaRepository<Asset, UUID> {
}
