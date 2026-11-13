package com.hrms.employee_service.dto.asset;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Education;
import com.hrms.employee_service.model.asset.Asset;
import com.hrms.employee_service.model.asset.Condition;
import com.hrms.employee_service.model.asset.Status;
import com.hrms.employee_service.repository.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AssetRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private UUID employeeId;
    private String name;
    private String code;
    private String description;
    private String category;
    private String brand;
    private String model;
    private String serialNumber;
    private LocalDate purchaseDate;
    private Double purchaseCost;
    private String currency;
    private Condition condition;
    private Status status;
    private String notes;

    public Asset toAsset(EmployeeRepository employeeRepository) {
        Asset asset = new Asset();
        asset.setEmployee(employeeId == null ? null : employeeRepository.getReferenceById(employeeId));
        asset.setName(name);
        asset.setCode(code);
        asset.setDescription(description);
        asset.setCategory(category);
        asset.setBrand(brand);
        asset.setModel(model);
        asset.setSerialNumber(serialNumber);
        asset.setPurchaseDate(purchaseDate);
        asset.setPurchaseCost(purchaseCost);
        asset.setCurrency(currency);
        asset.setCondition(condition);
        asset.setStatus(status);
        asset.setNotes(notes);
        return asset;
    }
}
