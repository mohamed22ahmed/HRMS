package com.hrms.employee_service.dto.branch;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Branch;
import com.hrms.employee_service.repository.AddressRepository;
import com.hrms.employee_service.repository.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BranchRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private String code;
    private String name;
    private String description;
    private String email;
    private String phone;
    private UUID addressId;
    private UUID managerId;
    private Boolean isHeadOffice;
    private String status;

    public Branch toBranch(EmployeeRepository employeeRepository, AddressRepository addressRepository) {
        Branch branch = new Branch();
        branch.setCode(code);
        branch.setName(name);
        branch.setDescription(description);
        branch.setEmail(email);
        branch.setPhone(phone);
        branch.setManager(managerId == null ? null : employeeRepository.getReferenceById(managerId));
        branch.setAddress(addressId == null ? null : addressRepository.getReferenceById(addressId));
        branch.setIsHeadOffice(isHeadOffice);
        branch.setStatus(status);
        return branch;
    }
}
