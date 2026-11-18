package com.hrms.employee_service.controller;

import com.hrms.employee_service.dto.address.AddressRequestDto;
import com.hrms.employee_service.dto.address.AddressResponseDto;
import com.hrms.employee_service.dto.common.ApiResponseDto;
import com.hrms.employee_service.model.Address;
import com.hrms.employee_service.service.AddressService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/addresses")
@CrossOrigin("*")
public class BranchController {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        List<Address> addresses = addressService.getAddresses();
        return ResponseEntity.ok(ApiResponseDto.successList(200, "Addresses retrieved successfully", addresses, AddressResponseDto::from));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> show(@PathVariable UUID id) {
        Address address = addressService.show(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Address retrieved successfully", AddressResponseDto.from(address)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@Validated({Default.class, AddressRequestDto.Create.class}) @RequestBody AddressRequestDto request) {
        Address address = addressService.store(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDto.success(201, "Address created successfully", AddressResponseDto.from(address)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable UUID id, @RequestBody AddressRequestDto request) {
        Address address = addressService.update(id, request);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Address updated successfully", AddressResponseDto.from(address)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) {
        addressService.delete(id);
        return ResponseEntity.ok(ApiResponseDto.success(200, "Address deleted successfully", Map.of("id", id)));
    }
}
