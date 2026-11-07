package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.employee.EmployeeRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Department;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.repository.EmployeeRepository;
import com.hrms.employee_service.repository.DepartmentRepository;
import com.hrms.employee_service.repository.LevelRepository;
import com.hrms.employee_service.repository.PositionRepository;
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
    private PositionRepository positionRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private Validator validator;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee show(UUID id) {
        return findEmployee(id);
    }

    public Employee store(EmployeeRequestDto request) {
        return employeeRepository.save(request.toEmployee(departmentRepository, positionRepository, levelRepository));
    }

    public Employee update(UUID id, EmployeeRequestDto request) {
        Employee employee = findEmployee(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, employee, RequestDto.nullProperties(request));
        if (request.getDepartmentId() != null) {
            employee.setDepartment(departmentRepository.getReferenceById(request.getDepartmentId()));
        }
        return employeeRepository.save(employee);
    }

    public void delete(UUID id) {
        employeeRepository.delete(findEmployee(id));
    }

    public Employee reassignToDepartment(UUID id, EmployeeRequestDto request) {
        Employee emp = findEmployee(id);
        UUID departmentId = request.getDepartmentId();
        if (departmentId == null) {
            throw new IllegalArgumentException("Department id is required");
        }
        emp.setDepartment(departmentRepository.getReferenceById(departmentId));
        return employeeRepository.save(emp);
    }

    public Employee dropFromDepartment(UUID id) {
        Employee emp = findEmployee(id);
        departmentRepository.findByManagerEmployeeId(id).ifPresent(department -> {
            department.setManagerEmployeeId(null);
            departmentRepository.save(department);
        });

        emp.setDepartment(null);
        return employeeRepository.save(emp);
    }

    public Employee reassignPosition(UUID id, EmployeeRequestDto request) {
        Employee emp = findEmployee(id);
        UUID positionId = request.getPositionId();
        if (positionId == null) {
            throw new IllegalArgumentException("Position id is required");
        }
        emp.setPosition(positionRepository.getReferenceById(positionId));
        return employeeRepository.save(emp);
    }

    public Employee dropPosition(UUID id) {
        Employee emp = findEmployee(id);
        emp.setPosition(null);
        return employeeRepository.save(emp);
    }

    public Employee reassignLevel(UUID id, EmployeeRequestDto request) {
        Employee emp = findEmployee(id);
        UUID levelId = request.getLevelId();
        if (levelId == null) {
            throw new IllegalArgumentException("Level id is required");
        }
        emp.setLevel(levelRepository.getReferenceById(levelId));
        return employeeRepository.save(emp);
    }

    public Employee dropLevel(UUID id) {
        Employee emp = findEmployee(id);
        emp.setLevel(null);
        return employeeRepository.save(emp);
    }

    public Employee changeStatus(UUID id, EmployeeRequestDto request) {
        Employee employee = findEmployee(id);
        employee.setEmploymentStatus(request.getEmploymentStatus());
        return employeeRepository.save(employee);
    }

    private Employee findEmployee(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }
}
