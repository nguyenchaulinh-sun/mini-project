
package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    private final UtilityService utilityService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(
            UtilityService utilityService,
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {
        this.utilityService = utilityService;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    // Create employee
    public Employee createEmployee(EmployeeRequest request) {
        Department department = getDepartment(request.getDepartmentId());

        Employee employee = new Employee();
        employee.setId(utilityService.generateEmployeeCode());
        employee.setName(utilityService.formatName(request.getName()));
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with ID: " + id));
    }

    // Update employee
    public Employee updateEmployee(String id, EmployeeRequest request) {
        Employee employee = getEmployeeById(id);
        Department department = getDepartment(request.getDepartmentId());

        employee.setName(utilityService.formatName(request.getName()));
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    // Delete employee
    public void deleteEmployee(String id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    // Search employee by name
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    // Search employee by department
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment_NameIgnoreCase(department);
    }

    // Find department by ID
    private Department getDepartment(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Department not found: " + departmentId));
    }

    // Search employees by name and department ID
    public List<Employee> searchEmployees(String name, Long departmentId) {

        List<Employee> employees;

        if (name != null && !name.isBlank()
                && departmentId != null) {

            employees = employeeRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .filter(employee -> employee.getDepartment() != null
                            && employee.getDepartment().getId().equals(departmentId))
                    .toList();

        } else if (name != null && !name.isBlank()) {

            employees = searchEmployeesByName(name);

        } else if (departmentId != null) {

            Department department = getDepartment(departmentId);
            employees = getEmployeesByDepartment(department.getName());

        } else {

            employees = getAllEmployees();
        }

        return employees;
    }
}
