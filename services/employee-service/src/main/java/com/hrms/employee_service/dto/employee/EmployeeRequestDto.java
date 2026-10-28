package com.hrms.employee_service.dto.employee;

import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.model.EmploymentStatus;
import com.hrms.employee_service.repository.EmployeeRepository;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@EmployeeRequestDto.UniqueEmployee
@Getter
@Setter
public class EmployeeRequestDto extends RequestDto {
    public interface Create extends RequestDto.Create {}

    @Constraint(validatedBy = UniqueEmployeeValidator.class)
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UniqueEmployee {
        String message() default "Employee email and number must be unique";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    public static class UniqueEmployeeValidator implements ConstraintValidator<UniqueEmployee, EmployeeRequestDto> {
        private final EmployeeRepository employeeRepository;

        public UniqueEmployeeValidator(EmployeeRepository employeeRepository) {
            this.employeeRepository = employeeRepository;
        }

        @Override
        public boolean isValid(EmployeeRequestDto request, ConstraintValidatorContext context) {
            if (request == null) {
                return true;
            }

            context.disableDefaultConstraintViolation();
            boolean valid = true;

            if (request.getEmail() != null
                    && isDuplicate(employeeRepository.findByEmail(request.getEmail()), request.getResourceId())) {
                addViolation(context, "email", "Email '" + request.getEmail() + "' already exists");
                valid = false;
            }
            if (request.getEmployeeNumber() != null && isDuplicate(
                    employeeRepository.findByEmployeeNumber(request.getEmployeeNumber()), request.getResourceId())) {
                addViolation(context, "employeeNumber",
                        "Employee number '" + request.getEmployeeNumber() + "' already exists");
                valid = false;
            }

            return valid;
        }

        private boolean isDuplicate(Optional<Employee> result, UUID currentEmployeeId) {
            return result.filter(employee -> !Objects.equals(employee.getId(), currentEmployeeId)).isPresent();
        }

        private void addViolation(ConstraintValidatorContext context, String field, String message) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(field)
                    .addConstraintViolation();
        }
    }

    @NotBlank(message = "Employee number is required", groups = Create.class)
    @Size(max = 30, message = "Employee number must be at most 30 characters")
    private String employeeNumber;

    @NotBlank(message = "First name is required", groups = Create.class)
    @Size(max = 100, message = "First name must be at most 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required", groups = Create.class)
    @Size(max = 100, message = "Last name must be at most 100 characters")
    private String lastName;

    @NotBlank(message = "Email is required", groups = Create.class)
    @Email(message = "Email must be a valid email address")
    private String email;

    private String phone;
    private UUID departmentId;
    private String jobTitle;

    @NotNull(message = "Hire date is required", groups = Create.class)
    private LocalDate hireDate;

    private EmploymentStatus employmentStatus;

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeNumber(employeeNumber);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setDepartmentId(departmentId);
        employee.setJobTitle(jobTitle);
        employee.setHireDate(hireDate);
        if (employmentStatus != null) {
            employee.setEmploymentStatus(employmentStatus);
        }
        return employee;
    }
}
