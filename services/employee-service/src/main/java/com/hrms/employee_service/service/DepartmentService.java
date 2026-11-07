package com.hrms.employee_service.service;

import com.hrms.employee_service.exception.ResourceNotFoundException;
import com.hrms.employee_service.dto.common.RequestDto;
import com.hrms.employee_service.dto.department.DepartmentRequestDto;
import com.hrms.employee_service.model.Department;
import com.hrms.employee_service.model.Employee;
import com.hrms.employee_service.model.Level;
import com.hrms.employee_service.model.Position;
import com.hrms.employee_service.repository.DepartmentRepository;
import com.hrms.employee_service.repository.EmployeeRepository;
import jakarta.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private Validator validator;
    @Autowired
    private EmployeeService employeeService;

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public record DepartmentEmployeesResponse(
            Department department,
            List<Employee> employees
    ) {}

    public DepartmentEmployeesResponse getDepartmentEmployees(UUID departmentId){
        Department dep = findDepartment(departmentId);
        List<Employee> employees =  dep.getEmployees();
        return new DepartmentEmployeesResponse(dep, employees);
    }

    public record DepartmentPositionsResponse(
            Department department,
            List<Position> positions
    ) {}

    public DepartmentPositionsResponse getDepartmentPositions(UUID departmentId){
        Department dep = findDepartment(departmentId);
        List<Position> positions = dep.getPositions();
        return new DepartmentPositionsResponse(dep, positions);
    }

    public record DepartmentLevelsResponse(
            Department department,
            List<Level> levels
    ) {}

    public DepartmentLevelsResponse getDepartmentLevels(UUID departmentId){
        Department dep = findDepartment(departmentId);
        List<Level> levels = dep.getLevels();
        return new DepartmentLevelsResponse(dep, levels);
    }

    public record DepartmentManagerResponse(
            String department_name,
            String employee_name
    ) {}

    public DepartmentManagerResponse setDepartmentManager(UUID id, DepartmentRequestDto request){
        Department dep = findDepartment(id);
        UUID employeeId = request.getManagerEmployeeId();

        Employee employee = employeeService.show(employeeId);
        employee.setDepartment(dep);
        employeeRepository.save(employee);

        dep.setManagerEmployeeId(employeeId);
        departmentRepository.save(dep);

        return new DepartmentManagerResponse(dep.getName(), employee.getFirstName() + " " + employee.getLastName());
    }

    public Department show(UUID id) {
        return findDepartment(id);
    }

    public Department store(DepartmentRequestDto request) {
        return departmentRepository.save(request.toDepartment());
    }

    public Department update(UUID id, DepartmentRequestDto request) {
        Department department = findDepartment(id);
        request.setResourceId(id);

        request.validate(validator);

        BeanUtils.copyProperties(request, department, RequestDto.nullProperties(request));
        return departmentRepository.save(department);
    }

    public void delete(UUID id) {
        departmentRepository.delete(findDepartment(id));
    }

    public Department changeStatus(UUID id, DepartmentRequestDto request) {
        Department department = findDepartment(id);
        department.setIsActive(request.getIsActive());
        return departmentRepository.save(department);
    }

    private Department findDepartment(UUID id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }
}
