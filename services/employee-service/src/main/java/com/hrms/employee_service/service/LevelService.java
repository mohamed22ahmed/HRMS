package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.level.LevelRequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Level;
import com.hrms.employee_service.repository.LevelRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LevelService {
    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private Validator validator;

    public List<Level> getLevels() {
        return levelRepository.findAll();
    }

    public Level show(UUID id) {
        return findLevel(id);
    }

    public Level store(LevelRequestDto request) {
        return levelRepository.save(request.toLevel());
    }

    public Level update(UUID id, LevelRequestDto request) {
        Level level = findLevel(id);
        request.setResourceId(id);

        request.validate(validator);
        BeanUtils.copyProperties(request, level, RequestDto.nullProperties(request));

        return levelRepository.save(level);
    }

    public Level changeStatus(UUID id, LevelRequestDto request) {
        Level level = findLevel(id);
        if (request.getIsActive() != null) {
            level.setIsActive(request.getIsActive());
        }
        return levelRepository.save(level);
    }

    public void delete(UUID id) {
        levelRepository.delete(findLevel(id));
    }

    private Level findLevel(UUID id) {
        return levelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Level not found"));
    }
}
