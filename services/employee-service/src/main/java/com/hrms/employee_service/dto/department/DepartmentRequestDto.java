package com.hrms.employee_service.dto.department;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Department;
import com.hrms.employee_service.repository.BranchRepository;
import com.hrms.employee_service.repository.DepartmentRepository;
import com.hrms.employee_service.repository.EmployeeRepository;
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
    private UUID managerId;
    private UUID branchId;
    private Boolean isActive;

    public Department toDepartment(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository, BranchRepository branchRepository) {
        Department department = new Department();
        department.setName(name);
        department.setCode(code);
        department.setDescription(description);
        department.setParentDepartment(parentDepartmentId == null ? null : departmentRepository.getReferenceById(parentDepartmentId));
        department.setManager(managerId == null ? null : employeeRepository.getReferenceById(managerId));
        department.setBranch(branchId == null ? null : branchRepository.getReferenceById(branchId));
        department.setIsActive(branchId == null || isActive);

        return department;
    }
}
