package com.hrms.employee_service.dto.department;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DepartmentRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    @NotBlank(message = "Department name is required", groups = Create.class)
    @Size(max = 100, message = "Department name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Department code is required", groups = Create.class)
    @Size(max = 100, message = "Department code must be at most 100 characters")
    private String code;

    @Size(max = 500, message = "Department description must be at most 500 characters")
    private String description;

    private UUID parentDepartmentId;
    private UUID managerEmployeeId;
    private Boolean isActive;

    public Department toDepartment() {
        Department department = new Department();
        department.setName(name);
        department.setCode(code);
        department.setDescription(description);
        department.setParentDepartmentId(parentDepartmentId);
        department.setManagerEmployeeId(managerEmployeeId);
        if (isActive != null) {
            department.setIsActive(isActive);
        }
        return department;
    }
}
