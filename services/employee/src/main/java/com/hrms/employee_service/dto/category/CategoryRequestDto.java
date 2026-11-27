package com.hrms.employee_service.dto.category;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class CategoryRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private UUID tenantId;
    private String name;
    private String description;

    public Category toCategory() {
        Category category = new Category();
        category.setTenantId(tenantId);
        category.setName(name);
        category.setDescription(description);
        return category;
    }
}
