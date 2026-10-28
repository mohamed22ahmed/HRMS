package com.hrms.employee_service.service;

import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.department.DepartmentRequestDto;
import com.hrms.employee_service.model.Department;
import com.hrms.employee_service.repository.DepartmentRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private Validator validator;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department show(UUID id) {
        return findDepartment(id);
    }

    public Department store(DepartmentRequestDto request) {
        return departmentRepository.save(request.toDepartment());
    }

    public Department update(UUID id, DepartmentRequestDto request) {
        Department department = findDepartment(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, department, RequestDto.nullProperties(request));
        return departmentRepository.save(department);
    }

    public void delete(UUID id) {
        departmentRepository.delete(findDepartment(id));
    }

    private Department findDepartment(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

}
