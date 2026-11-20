package com.hrms.employee_service.service;

import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.department.DepartmentRequestDto;
import com.hrms.employee_service.model.*;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.repository.BranchRepository;
import com.hrms.employee_service.repository.DepartmentRepository;
import com.hrms.employee_service.repository.EmployeeRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private Validator validator;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department show(UUID id) {
        return findDepartment(id);
    }

    public Department store(DepartmentRequestDto request) {
        return departmentRepository.save(request.toDepartment(departmentRepository, employeeRepository, branchRepository));
    }

    public Department update(UUID id, DepartmentRequestDto request) {
        Department department = findDepartment(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, department, RequestDto.nullProperties(request));
        if (request.getManagerId() != null) {
            department.setManager(employeeRepository.getReferenceById(request.getManagerId()));
        }
        if (request.getParentDepartmentId() != null) {
            department.setParentDepartment(departmentRepository.getReferenceById(request.getParentDepartmentId()));
        }
        if (request.getBranchId() != null) {
            department.setBranch(branchRepository.getReferenceById(request.getBranchId()));
        }
        return departmentRepository.save(department);
    }

    public void delete(UUID id) {
        departmentRepository.delete(findDepartment(id));
    }

    public Employee setDepartmentManager(UUID id, DepartmentRequestDto request){
        Department department = findDepartment(id);
        Employee manager = employeeRepository.getReferenceById(request.getManagerId());
        department.setManager(manager);
        manager.setDepartment(department);
        employeeRepository.save(manager);
        return manager;
    }

    public Department changeStatus(UUID id, DepartmentRequestDto request) {
        Department department = findDepartment(id);
        department.setIsActive(request.getIsActive());
        return departmentRepository.save(department);
    }

    public List<Employee> getEmployees(UUID department_id){
        return findDepartment(department_id).getEmployees();
    }

    public List<Position> getPositions(UUID department_id){
        return findDepartment(department_id).getPositions();
    }

    public List<Level> getLevels(UUID department_id){
        return findDepartment(department_id).getLevels();
    }

    public List<Work> getWorks(UUID department_id){
        return findDepartment(department_id).getEmployees().stream()
                .map(Employee::getWork)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<Education> getEducations(UUID department_id) {
        return findDepartment(department_id).getEmployees().stream()
                .map(Employee::getEducation)
                .filter(Objects::nonNull)
                .toList();
    }

    public Branch getBranch(UUID department_id){
        return findDepartment(department_id).getBranch();
    }

    public Department getParentDepartment(UUID department_id){
        return findDepartment(department_id).getParentDepartment();
    }

    public Employee getManager(UUID department_id){
        return findDepartment(department_id).getManager();
    }

    private Department findDepartment(UUID id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }
}
