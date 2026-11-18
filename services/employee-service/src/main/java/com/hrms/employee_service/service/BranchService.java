package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.address.AddressRequestDto;
import com.hrms.employee_service.dto.branch.BranchRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Address;
import com.hrms.employee_service.model.Branch;
import com.hrms.employee_service.repository.AddressRepository;
import com.hrms.employee_service.repository.BranchRepository;
import com.hrms.employee_service.repository.EmployeeRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private Branch findBranch(UUID id) {
        return branchRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
    }
}
