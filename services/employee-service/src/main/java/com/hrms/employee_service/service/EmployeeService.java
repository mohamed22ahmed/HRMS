package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.employee.EmployeeRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.repository.EmployeeRepository;
import com.hrms.employee_service.repository.DepartmentRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private Validator validator;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee show(UUID id) {
        return findEmployee(id);
    }

    public Employee store(EmployeeRequestDto request) {
        return employeeRepository.save(request.toEmployee(departmentRepository));
    }

    public Employee update(UUID id, EmployeeRequestDto request) {
        Employee employee = findEmployee(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, employee, RequestDto.nullProperties(request));
        return employeeRepository.save(employee);
    }

    public void delete(UUID id) {
        employeeRepository.delete(findEmployee(id));
    }

    private Employee findEmployee(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

}
