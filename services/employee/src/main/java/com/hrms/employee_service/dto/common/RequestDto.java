package com.hrms.employee_service.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public abstract class RequestDto {
    public interface Create {}

    @JsonIgnore
    private UUID resourceId;

    public void validate(Validator validator) {
        Set<ConstraintViolation<RequestDto>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            Set<ConstraintViolation<?>> errors = new HashSet<>(violations);
            throw new ConstraintViolationException("Validation errors", errors);
        }
    }

    public static String[] nullProperties(Object source) {
        BeanWrapper wrapper = new BeanWrapperImpl(source);
        return Arrays.stream(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(name -> wrapper.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }
}
