package com.example.employee_management.repository;

import com.example.employee_management.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public Employee save(Employee employee) {
        employees.add(employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    public Optional<Employee> findById(String id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }
}