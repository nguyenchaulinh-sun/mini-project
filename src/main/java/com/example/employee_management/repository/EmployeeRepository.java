package com.example.employee_management.repository;

import com.example.employee_management.dto.DepartmentStatisticsDTO;
import com.example.employee_management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.employee_management.dto.DepartmentStatisticsDTO;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    // Search employees by name
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Search employees by department name
    List<Employee> findByDepartment_NameIgnoreCase(String departmentName);

    Optional<Employee> findTopByOrderByIdDesc();


    @Query("""
        SELECT new com.example.employee_management.dto.DepartmentStatisticsDTO(
            d.name,
            COUNT(e.id)
        )
        FROM Employee e
        JOIN e.department d
        GROUP BY d.name
        ORDER BY d.name
    """)
    List<DepartmentStatisticsDTO> getEmployeeCountByDepartment();

}
