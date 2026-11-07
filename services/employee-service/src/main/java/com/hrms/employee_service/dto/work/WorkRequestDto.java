package com.hrms.employee_service.dto.work;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Work;
import com.hrms.employee_service.repository.EmployeeRepository;
import com.hrms.employee_service.repository.LevelRepository;
import com.hrms.employee_service.repository.PositionRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class WorkRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private UUID employeeId;
    private UUID positionId;
    private UUID levelId;
    private LocalDate hireDate;

    public Work toWork(EmployeeRepository employeeRepository, PositionRepository positionRepository, LevelRepository levelRepository) {
        Work work = new Work();
        work.setEmployee(employeeId == null ? null : employeeRepository.getReferenceById(employeeId));
        work.setPosition(positionId == null ? null : positionRepository.getReferenceById(positionId));
        work.setLevel(levelId == null ? null : levelRepository.getReferenceById(levelId));
        work.setHireDate(hireDate);
        return work;
    }
}
