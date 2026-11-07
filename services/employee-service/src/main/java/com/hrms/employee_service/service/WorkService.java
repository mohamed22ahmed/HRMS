package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.employee.EmployeeRequestDto;
import com.hrms.employee_service.dto.work.WorkRequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.model.Work;
import com.hrms.employee_service.repository.*;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WorkService {
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private Validator validator;

    public List<Work> getWorks() {
        return workRepository.findAll();
    }

    public Work show(UUID id) {
        return findWork(id);
    }

    public Work store(WorkRequestDto request) {
        return workRepository.save(request.toWork(employeeRepository, positionRepository, levelRepository));
    }

    public Work update(UUID id, WorkRequestDto request) {
        Work work = findWork(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, work, RequestDto.nullProperties(request));
        if (request.getEmployeeId() != null) {
            work.setEmployee(employeeRepository.getReferenceById(request.getEmployeeId()));
        }
        if (request.getPositionId() != null) {
            work.setPosition(positionRepository.getReferenceById(request.getPositionId()));
        }
        if (request.getLevelId() != null) {
            work.setLevel(levelRepository.getReferenceById(request.getLevelId()));
        }
        return workRepository.save(work);
    }

    public void delete(UUID id) {
        workRepository.delete(findWork(id));
    }

    public Work reassignPosition(UUID id, WorkRequestDto request) {
        Work work = findWork(id);
        UUID positionId = request.getPositionId();
        if (positionId == null) {
            throw new IllegalArgumentException("Position id is required");
        }
        work.setPosition(positionRepository.getReferenceById(positionId));
        return workRepository.save(work);
    }

    public Work dropPosition(UUID id) {
        Work work = findWork(id);
        work.setPosition(null);
        return workRepository.save(work);
    }

    public Work reassignLevel(UUID id, WorkRequestDto request) {
        Work work = findWork(id);
        UUID levelId = request.getLevelId();
        if (levelId == null) {
            throw new IllegalArgumentException("Level id is required");
        }
        work.setLevel(levelRepository.getReferenceById(levelId));
        return workRepository.save(work);
    }

    public Work dropLevel(UUID id) {
        Work work = findWork(id);
        work.setLevel(null);
        return workRepository.save(work);
    }

    private Work findWork(UUID id) {
        return workRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Work not found"));
    }
}
