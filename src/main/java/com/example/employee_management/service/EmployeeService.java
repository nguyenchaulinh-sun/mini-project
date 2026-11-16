package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final UtilityService utilityService;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(
            UtilityService utilityService,
            ModelMapper modelMapper,
            EmployeeRepository employeeRepository
    ) {
        this.utilityService = utilityService;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(EmployeeRequest request) {
        Employee employee = modelMapper.map(request, Employee.class);

        employee.setId(utilityService.generateEmployeeCode());
        employee.setName(utilityService.formatName(request.getName()));

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Employee not found: " + id
                        )
                );
    }
}