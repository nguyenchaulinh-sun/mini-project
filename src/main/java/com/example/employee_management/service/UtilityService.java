package com.example.employee_management.service;

import com.example.employee_management.entity.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

    private final EmployeeRepository employeeRepository;

    public UtilityService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String generateEmployeeCode() {
        long nextNumber = employeeRepository.findTopByOrderByIdDesc()
                .map(Employee::getId)
                .map(id -> Long.parseLong(id.substring(3)))
                .orElse(0L) + 1;

        return String.format("EMP%04d", nextNumber);
    }

    public String formatName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Employee name cannot be empty"
            );
        }

        return name.trim().toUpperCase();
    }
}
