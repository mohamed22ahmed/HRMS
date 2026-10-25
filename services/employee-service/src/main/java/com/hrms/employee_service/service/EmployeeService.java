package com.hrms.employee_service.service;

import com.hrms.employee_service.controller.EmployeeController;
import com.hrms.employee_service.entity.Employee;
import com.hrms.employee_service.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
        String[] ignored = nullProperties(employee);
        logger.info("Ignored null properties: {}", Arrays.toString(ignored));

        return employeeRepository.findById(id).map(existing -> {
            BeanUtils.copyProperties(employee, existing, nullProperties(employee));
            return employeeRepository.save(existing);
        }).orElse(null);
    }

    private String[] nullProperties(Object source) {
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        return java.util.Arrays.stream(wrapper.getPropertyDescriptors())
                .map(java.beans.PropertyDescriptor::getName)
                .filter(name -> wrapper.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }
}
