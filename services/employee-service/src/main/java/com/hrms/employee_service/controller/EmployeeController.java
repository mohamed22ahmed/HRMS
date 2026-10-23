package com.hrms.employee_service.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin("*")
public class EmployeeController {

    public record Employee(
            Long id,
            String fullName,
            String email,
            String department
    ) {}

    @GetMapping
    public List<Employee> getEmployees() {
        return List.of(
                new Employee(
                        1L,
                        "Ahmed Mohamed",
                        "ahmed@example.com",
                        "Engineering"
                ),
                new Employee(
                        2L,
                        "Sara Ali",
                        "sara@example.com",
                        "Human Resources"
                )
        );
    }
}