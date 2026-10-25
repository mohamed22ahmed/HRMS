package com.hrms.employee_service.controller;

import com.hrms.employee_service.entity.Employee;
import com.hrms.employee_service.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{id}")
    public Employee show(@PathVariable UUID id) {
        return employeeService.show(id);
    }

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return employeeService.store(employee);
    }

    @PatchMapping("/{id}")
    public Employee update(@PathVariable UUID id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        employeeService.delete(id);
    }
}