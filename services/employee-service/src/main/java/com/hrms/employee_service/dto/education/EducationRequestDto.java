package com.hrms.employee_service.dto.education;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Education;
import com.hrms.employee_service.repository.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class EducationRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private UUID employeeId;
    private String university;
    private String degree;
    private String department;
    private LocalDate graduateDate;

    public Education toEducation(EmployeeRepository employeeRepository) {
        Education education = new Education();
        education.setEmployee(employeeId == null ? null : employeeRepository.getReferenceById(employeeId));
        education.setUniversity(university);
        education.setDegree(degree);
        education.setDepartment(department);
        education.setGraduateDate(graduateDate);
        return education;
    }
}
