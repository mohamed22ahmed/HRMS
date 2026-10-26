package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.EmployeeRequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.repository.EmployeeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Validator validator;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee show(UUID id) {
        return findEmployee(id);
    }

    public Employee store(EmployeeRequestDto request) {
        return employeeRepository.save(request.toEmployee());
    }

    public Employee update(UUID id, EmployeeRequestDto request) {
        Employee employee = findEmployee(id);
        request.setEmployeeId(id);

        Set<ConstraintViolation<EmployeeRequestDto>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            Set<ConstraintViolation<?>> errors = new HashSet<>(violations);
            throw new ConstraintViolationException("Validation errors", errors);
        }

        BeanUtils.copyProperties(request, employee, nullProperties(request));
        return employeeRepository.save(employee);
    }

    public void delete(UUID id) {
        employeeRepository.delete(findEmployee(id));
    }

    private Employee findEmployee(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    private String[] nullProperties(Object source) {
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        return Arrays.stream(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> wrapper.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
