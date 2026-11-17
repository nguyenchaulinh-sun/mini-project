package com.example.employee_management.repository;

import com.example.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    // Search employees by name
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Search employees by department name
    List<Employee> findByDepartment_NameIgnoreCase(String departmentName);

    Optional<Employee> findTopByOrderByIdDesc();
}
