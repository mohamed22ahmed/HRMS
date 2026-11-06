package com.hrms.employee_service.dto.level;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Level;
import com.hrms.employee_service.model.Position;
import com.hrms.employee_service.repository.LevelRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Getter
@Setter
@Component
public class LevelRequestDto extends RequestDto {
    @Autowired
    private LevelRepository levelRepository;

    public interface Create extends RequestDto.Create {}

    @NotBlank(message = "Level name is required", groups = Create.class)
    @Size(max = 100, message = "Level name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Level code is required", groups = Create.class)
    @Size(max = 100, message = "Level code must be at most 100 characters")
    private String code;

    private Boolean isActive;

    public Level toLevel()  {
        Level level = new Level();
        level.setName(name);
        level.setCode(code);
        level.setIsActive(isActive);
        return level;
    }
}
