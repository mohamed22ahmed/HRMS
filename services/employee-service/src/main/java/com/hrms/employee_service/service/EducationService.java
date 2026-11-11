package com.hrms.employee_service.service;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.education.EducationRequestDto;
import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.model.Education;
import com.hrms.employee_service.repository.*;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EducationService {
    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Validator validator;

    public List<Education> getEducations() {
        return educationRepository.findAll();
    }

    public Education show(UUID id) {
        return findEducation(id);
    }

    public Education store(EducationRequestDto request) {
        return educationRepository.save(request.toEducation(employeeRepository));
    }

    public Education update(UUID id, EducationRequestDto request) {
        Education education = findEducation(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, education, RequestDto.nullProperties(request));
        if (request.getEmployeeId() != null) {
            education.setEmployee(employeeRepository.getReferenceById(request.getEmployeeId()));
        }
        return educationRepository.save(education);
    }

    public void delete(UUID id) {
        educationRepository.delete(findEducation(id));
    }

    private Education findEducation(UUID id) {
        return educationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Education not found"));
    }
}
