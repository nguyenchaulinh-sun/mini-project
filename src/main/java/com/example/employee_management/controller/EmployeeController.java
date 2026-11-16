package com.example.employee_management.controller;

import com.example.employee_management.component.AppInfoComponent;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AppInfoComponent appInfoComponent;

    @Autowired
    public EmployeeController(
            EmployeeService employeeService,
            AppInfoComponent appInfoComponent
    ) {
        this.employeeService = employeeService;
        this.appInfoComponent = appInfoComponent;
    }

    // POST: Add a new employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestBody EmployeeRequest request
    ) {
        Employee employee = employeeService.createEmployee(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employee);
    }

    // GET: Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // GET: Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable String id
    ) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    // GET: Search employees by department
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam String department
    ) {
        return ResponseEntity.ok(
                employeeService.getEmployeesByDepartment(department)
        );
    }

    // GET: Application information
    @GetMapping("/info")
    public ResponseEntity<String> getApplicationInfo() {
        String info = appInfoComponent.getApplicationName()
                + " - "
                + appInfoComponent.getModule();

        return ResponseEntity.ok(info);
    }
}
