package com.example.employee_management.controller;

import com.example.employee_management.component.AppInfoComponent;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.service.EmployeeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AppInfoComponent appInfoComponent;

    public EmployeeController(
            EmployeeService employeeService,
            AppInfoComponent appInfoComponent
    ) {
        this.employeeService = employeeService;
        this.appInfoComponent = appInfoComponent;
    }

    // POST: Create employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @RequestBody EmployeeRequest request
    ) {
        Employee employee = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    // GET: Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // GET: Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    // PUT: Update employee
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable String id,
            @RequestBody EmployeeRequest request
    ) {
        try {
            Employee employee = employeeService.updateEmployee(id, request);
            return ResponseEntity.ok(employee);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    // DELETE: Delete employee
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }

    // GET: Search employees by name or department
    @GetMapping("/search")
    public ResponseEntity<?> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department
    ) {
        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(
                    employeeService.searchEmployeesByName(name)
            );
        }

        if (department != null && !department.isBlank()) {
            return ResponseEntity.ok(
                    employeeService.getEmployeesByDepartment(department)
            );
        }

        return ResponseEntity.badRequest()
                .body("Please provide name or department parameter");
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
