package com.example.employee_management.service;

import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final EmployeeRepository employeeRepository;

    public ReportService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Cacheable(value = "employeeCount")
    public long getTotalEmployees() {
        return employeeRepository.count();
    }
}