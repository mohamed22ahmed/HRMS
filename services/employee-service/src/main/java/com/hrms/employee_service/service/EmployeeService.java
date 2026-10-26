package com.hrms.employee_service.service;

import com.hrms.employee_service.entity.Employee;
import com.hrms.employee_service.repository.EmployeeRepository;
import com.hrms.employee_service.util.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee show(UUID id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.orElse(null);
    }

    public Employee store(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee update(UUID id, Employee employee) {
        return employeeRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(employee, existing, GeneralUtils.nullProperties(employee));
            return employeeRepository.save(existing);
        }).orElse(null);
    }

    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }
}
