package com.hrms.employee_service.controller;

import com.hrms.employee_service.entity.Employee;
import com.hrms.employee_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> index() {
        return employeeService.getEmployees();
    }

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        employeeService.store(employee);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody Employee employee) {
        employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        employeeService.delete(id);
    }

}