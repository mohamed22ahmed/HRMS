package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.category.CategoryRequestDto;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Category;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.repository.CategoryRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Validator validator;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Asset> categoryAssets(UUID id){
        return findCategory(id).getAssets();
    }

    public Category show(UUID id) {
        return findCategory(id);
    }

    public Category store(CategoryRequestDto request) {
        return categoryRepository.save(request.toCategory());
    }

    public Category update(UUID id, CategoryRequestDto request) {
        Category category = findCategory(id);
        request.setResourceId(id);
        request.validate(validator);
        BeanUtils.copyProperties(request, category, RequestDto.nullProperties(request));
        return categoryRepository.save(category);
    }

    public void delete(UUID id) {
        categoryRepository.delete(findCategory(id));
    }

    private Category findCategory(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
