package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.address.AddressRequestDto;
import com.hrms.employee_service.dto.branch.BranchRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.department.DepartmentRequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.*;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.repository.AddressRepository;
import com.hrms.employee_service.repository.BranchRepository;
import com.hrms.employee_service.repository.EmployeeRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Validator validator;

    public List<Branch> getBranches() {
        return branchRepository.findAll();
    }

    public Branch show(UUID id) {
        return findBranch(id);
    }

    public Branch store(BranchRequestDto request) {
        return branchRepository.save(request.toBranch(employeeRepository, addressRepository));
    }

    public Branch update(UUID id, BranchRequestDto request) {
        Branch branch = findBranch(id);
        request.setResourceId(id);
        request.validate(validator);
        BeanUtils.copyProperties(request, branch, RequestDto.nullProperties(request));
        return branchRepository.save(branch);
    }

    public void delete(UUID id) {
        branchRepository.delete(findBranch(id));
    }

    public Employee setManager(UUID id, BranchRequestDto request){
        Branch branch = findBranch(id);
        Employee manager = employeeRepository.getReferenceById(request.getManagerId());
        branch.setManager(manager);
        return manager;
    }

    public Branch changeStatus(UUID id, BranchRequestDto request) {
        Branch branch = findBranch(id);
        branch.setStatus(request.getStatus());
        return branchRepository.save(branch);
    }

    public Branch setHeadOffice(UUID id, BranchRequestDto request) {
        Branch branch = findBranch(id);
        branch.setIsHeadOffice(request.getIsHeadOffice());
        return branchRepository.save(branch);
    }

    public List<Department> getDepartments(UUID branch_id){
        return findBranch(branch_id).getDepartments();
    }

    public List<Employee> getEmployees(UUID branch_id){
        return findBranch(branch_id).getDepartments().stream()
                .flatMap(department -> department.getEmployees().stream())
                .toList();
    }

    public List<Position> getPositions(UUID branch_id){
        return findBranch(branch_id).getDepartments().stream()
                .flatMap(department -> department.getPositions().stream())
                .toList();
    }

    public List<Level> getLevels(UUID branch_id){
        return findBranch(branch_id).getDepartments().stream()
                .flatMap(department -> department.getLevels().stream())
                .toList();
    }

    public List<Work> getWorks(UUID branch_id){
        return findBranch(branch_id).getDepartments().stream()
                .flatMap(department -> department.getEmployees().stream())
                .map(Employee::getWork)
                .toList();
    }

    public List<Education> getEducations(UUID branch_id) {
        return findBranch(branch_id).getDepartments().stream()
                .flatMap(department -> department.getEmployees().stream())
                .map(Employee::getEducation)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<Asset> getAssets(UUID branch_id){
        return findBranch(branch_id).getDepartments().stream()
                .flatMap(department -> department.getEmployees().stream())
                .flatMap(employee -> employee.getAssets().stream())
                .toList();
    }

    public Address getAddress(UUID id){
        return findBranch(id).getAddress();
    }

    public Employee getManager(UUID branch_id){
        return findBranch(branch_id).getManager();
    }

    private Branch findBranch(UUID id) {
        return branchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }
}
