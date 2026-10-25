package com.hrms.employee_service.service;

import com.hrms.employee_service.controller.EmployeeController;
import com.hrms.employee_service.entity.Employee;
import com.hrms.employee_service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void store(Employee employee) {
        employeeRepository.save(employee);
    }

    public void update(UUID id, Employee employee) {
        Optional<Employee> emp = employeeRepository.findById(id);

        if (emp.isPresent()) {
            employeeRepository.save(employee);
        }
    }

    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }
}
