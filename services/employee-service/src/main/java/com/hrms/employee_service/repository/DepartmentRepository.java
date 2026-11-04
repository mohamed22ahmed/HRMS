package com.hrms.employee_service.repository;

import com.hrms.employee_service.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByManagerEmployeeId(UUID managerEmployeeId);

}
