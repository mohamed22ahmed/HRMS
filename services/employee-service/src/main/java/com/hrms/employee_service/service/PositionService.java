package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.position.PositionRequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Department;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.model.Position;
import com.hrms.employee_service.model.Work;
import com.hrms.employee_service.repository.DepartmentRepository;
import com.hrms.employee_service.repository.PositionRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PositionService {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private Validator validator;

    public List<Position> getPositions() {
        return positionRepository.findAll();
    }

    public record PositionEmployeesResponse(
            Position position,
            List<Employee> employees
    ) {}

    public PositionService.PositionEmployeesResponse getPositionEmployees(UUID positionId){
        Position position = findPosition(positionId);
        List<Employee> employees = position.getWorks().stream()
                .map(Work::getEmployee)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        return new PositionService.PositionEmployeesResponse(position, employees);
    }

    public Position show(UUID id) {
        return findPosition(id);
    }

    public Position store(PositionRequestDto request) {
        return positionRepository.save(request.toPosition(departmentRepository));
    }

    public Position update(UUID id, PositionRequestDto request) {
        Position position = findPosition(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, position, RequestDto.nullProperties(request));
        if (request.getDepartmentId() != null) {
            position.setDepartment(departmentRepository.getReferenceById(request.getDepartmentId()));
        }
        return positionRepository.save(position);
    }

    public Position reassignToDepartment(UUID id, PositionRequestDto request) {
        Position position = findPosition(id);
        if (request.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department id is required");
        }

        UUID departmentId = request.getDepartmentId();
        position.setDepartment(departmentRepository.getReferenceById(departmentId));
        return positionRepository.save(position);
    }

    public Position dropFromDepartment(UUID id) {
        Position position = findPosition(id);
        position.setDepartment(null);
        return positionRepository.save(position);
    }

    public Position changeStatus(UUID id, PositionRequestDto request) {
        Position position = findPosition(id);
        if (request.getIsActive() != null) {
            position.setIsActive(request.getIsActive());
        }
        return positionRepository.save(position);
    }

    public void delete(UUID id) {
        positionRepository.delete(findPosition(id));
    }

    private Position findPosition(UUID id) {
        return positionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Position not found"));
    }
}
