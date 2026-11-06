package com.hrms.employee_service.dto.position;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Position;
import com.hrms.employee_service.repository.PositionRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.hrms.employee_service.repository.DepartmentRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter
@Component
public class PositionRequestDto extends RequestDto {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public interface Create extends RequestDto.Create {}

    @NotBlank(message = "Position name is required", groups = Create.class)
    @Size(max = 100, message = "Position name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Position code is required", groups = Create.class)
    @Size(max = 100, message = "Position code must be at most 100 characters")
    private String code;

    @NotBlank(message = "Position level is required", groups = Create.class)
    @Size(max = 100, message = "Position level must be at most 100 characters")
    private String level;


    private UUID departmentId;
    private Boolean isActive;

    public Position toPosition(DepartmentRepository departmentRepository)  {
        Position position = new Position();
        position.setName(name);
        position.setCode(code);
        position.setLevel(level);
        position.setDepartment(departmentId == null ? null : departmentRepository.getReferenceById(departmentId));
        if (isActive != null) {
            position.setIsActive(isActive);
        }
        return position;
    }
}
