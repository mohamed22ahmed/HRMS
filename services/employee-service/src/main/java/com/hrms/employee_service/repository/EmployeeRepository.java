package com.hrms.employee_service.repository;

import com.hrms.employee_service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, UUID> {

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByEmployeeNumber(String employeeNumber);

    List<Employee> findByDepartmentId(UUID departmentId);
}
