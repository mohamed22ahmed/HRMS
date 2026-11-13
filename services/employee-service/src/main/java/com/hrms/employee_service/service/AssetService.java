package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.asset.AssetRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.repository.AssetRepository;
import com.hrms.employee_service.repository.EmployeeRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Validator validator;

    public List<Asset> getAssets() {
        return assetRepository.findAll();
    }

    public Asset show(UUID id) {
        return findAsset(id);
    }

    public Asset store(AssetRequestDto request) {
        return assetRepository.save(request.toAsset(employeeRepository));
    }

    public Asset update(UUID id, AssetRequestDto request) {
        Asset asset = findAsset(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, asset, RequestDto.nullProperties(request));
        if (request.getEmployeeId() != null) {
            asset.setEmployee(employeeRepository.getReferenceById(request.getEmployeeId()));
        }
        return assetRepository.save(asset);
    }

    public void delete(UUID id) {
        assetRepository.delete(findAsset(id));
    }

    public Asset changeStatus(UUID id, AssetRequestDto request) {
        Asset asset = findAsset(id);
        asset.setStatus(request.getStatus());
        return assetRepository.save(asset);
    }

    public Asset changeCondition(UUID id, AssetRequestDto request) {
        Asset asset = findAsset(id);
        asset.setCondition(request.getCondition());
        return assetRepository.save(asset);
    }

    private Asset findAsset(UUID id) {
        return assetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Asset not found"));
    }
}
