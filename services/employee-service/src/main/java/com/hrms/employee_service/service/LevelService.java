package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.level.LevelRequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.model.Level;
import com.hrms.employee_service.model.Position;
import com.hrms.employee_service.repository.DepartmentRepository;
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
    private DepartmentRepository departmentRepository;

    @Autowired
    private Validator validator;

    public List<Level> getLevels() {
        return levelRepository.findAll();
    }

    public record LevelEmployeesResponse(
            Level level,
            List<Employee> employees
    ) {}

    public LevelService.LevelEmployeesResponse getLevelEmployees(UUID levelId){
        Level level = findLevel(levelId);
        List<Employee> employees = level.getEmployees();
        return new LevelService.LevelEmployeesResponse(level, employees);
    }

    public Level show(UUID id) {
        return findLevel(id);
    }

    public Level store(LevelRequestDto request) {
        return levelRepository.save(request.toLevel(departmentRepository));
    }

    public Level update(UUID id, LevelRequestDto request) {
        Level level = findLevel(id);
        request.setResourceId(id);

        request.validate(validator);
        BeanUtils.copyProperties(request, level, RequestDto.nullProperties(request));
        if (request.getDepartmentId() != null) {
            level.setDepartment(departmentRepository.getReferenceById(request.getDepartmentId()));
        }

        return levelRepository.save(level);
    }

    public Level changeStatus(UUID id, LevelRequestDto request) {
        Level level = findLevel(id);
        if (request.getIsActive() != null) {
            level.setIsActive(request.getIsActive());
        }
        return levelRepository.save(level);
    }

    public Level reassignToDepartment(UUID id, LevelRequestDto request) {
        Level level = findLevel(id);
        if (request.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department id is required");
        }

        UUID departmentId = request.getDepartmentId();
        level.setDepartment(departmentRepository.getReferenceById(departmentId));
        return levelRepository.save(level);
    }

    public Level dropFromDepartment(UUID id) {
        Level level = findLevel(id);
        level.setDepartment(null);
        return levelRepository.save(level);
    }

    public void delete(UUID id) {
        levelRepository.delete(findLevel(id));
    }

    private Level findLevel(UUID id) {
        return levelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Level not found"));
    }
}
