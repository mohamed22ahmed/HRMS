package com.hrms.employee_service.repository;

import com.hrms.employee_service.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<Work, UUID> {
}
