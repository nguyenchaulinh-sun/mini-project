
package com.example.employee_management.service;

import com.example.employee_management.dto.DepartmentStatisticsDTO;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final EmployeeRepository employeeRepository;

    public StatisticsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Total employees in the system
    public long getTotalEmployees() {
        return employeeRepository.count();
    }

    // Employee count grouped by department
    public List<DepartmentStatisticsDTO> getEmployeeCountByDepartment() {
        return employeeRepository.getEmployeeCountByDepartment();
    }
}
