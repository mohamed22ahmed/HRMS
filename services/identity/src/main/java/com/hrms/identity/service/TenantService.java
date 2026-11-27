package com.hrms.identity.service;

import com.hrms.identity.dto.common.RequestDto;
import com.hrms.identity.dto.tenant.TenantRequestDto;
import com.hrms.identity.exception.ResourceNotFoundException;
import com.hrms.identity.model.Tenant;
import com.hrms.identity.repository.TenantRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private Validator validator;

    public List<Tenant> getTenants() {
        return tenantRepository.findAll();
    }

    public Tenant show(UUID id) {
        return findCategory(id);
    }

    public Tenant store(TenantRequestDto request) {
        return tenantRepository.save(request.toTenant());
    }

    public Tenant update(UUID id, TenantRequestDto request) {
        Tenant category = findCategory(id);
        request.setResourceId(id);
        request.validate(validator);
        BeanUtils.copyProperties(request, category, RequestDto.nullProperties(request));
        return tenantRepository.save(category);
    }

    public void delete(UUID id) {
        tenantRepository.delete(findCategory(id));
    }

    private Tenant findCategory(UUID id) {
        return tenantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
    }
}
