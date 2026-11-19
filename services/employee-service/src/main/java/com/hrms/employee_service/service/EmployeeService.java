package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.employee.EmployeeRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.*;
import com.hrms.employee_service.model.asset.Asset;
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
            department.setManager(null);
            departmentRepository.save(department);
        });

        emp.setDepartment(null);
        return employeeRepository.save(emp);
    }

    public Employee changeStatus(UUID id, EmployeeRequestDto request) {
        Employee employee = findEmployee(id);
        employee.setEmploymentStatus(request.getEmploymentStatus());
        return employeeRepository.save(employee);
    }

    public Branch getBranch(UUID employee_id){
        return findEmployee(employee_id).getDepartment().getBranch();
    }

    public Department getDepartment(UUID employee_id){
        return findEmployee(employee_id).getDepartment();
    }

    public Position getPosition(UUID employee_id){
        return findEmployee(employee_id).getWork().getPosition();
    }

    public Level getLevel(UUID employee_id){
        return findEmployee(employee_id).getWork().getLevel();
    }

    public Work getWork(UUID employee_id){
        return findEmployee(employee_id).getWork();
    }

    public Education getEducation(UUID employee_id){
        return findEmployee(employee_id).getEducation();
    }

    public List<Asset> getAssets(UUID employee_id){
        return findEmployee(employee_id).getAssets();
    }

    private Employee findEmployee(UUID id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }
}
