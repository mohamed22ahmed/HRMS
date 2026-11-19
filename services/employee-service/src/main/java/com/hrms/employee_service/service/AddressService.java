package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.address.AddressRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Address;
import com.hrms.employee_service.model.Branch;
import com.hrms.employee_service.repository.AddressRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private Validator validator;

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address show(UUID id) {
        return findAddress(id);
    }

    public Address store(AddressRequestDto request) {
        return addressRepository.save(request.toAddress());
    }

    public Address update(UUID id, AddressRequestDto request) {
        Address address = findAddress(id);
        request.setResourceId(id);
        request.validate(validator);
        BeanUtils.copyProperties(request, address, RequestDto.nullProperties(request));
        return addressRepository.save(address);
    }

    public void delete(UUID id) {
        addressRepository.delete(findAddress(id));
    }

    public Branch getBranch(UUID work_id){
        return findAddress(work_id).getBranch();
    }

    private Address findAddress(UUID id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }
}
