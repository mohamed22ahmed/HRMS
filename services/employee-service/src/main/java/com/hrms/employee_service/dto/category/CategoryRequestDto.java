package com.hrms.employee_service.dto.category;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Category;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoryRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    private String name;
    private String description;

    public Category toCategory() {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }
}
