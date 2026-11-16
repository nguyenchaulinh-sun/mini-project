package com.example.employee_management.controller;

import com.example.employee_management.component.AppInfoComponent;
import com.example.employee_management.dto.EmployeeRequest;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/info")
    public String getApplicationInfo() {
        return appInfoComponent.getApplicationName()
                + " - "
                + appInfoComponent.getModule();
    }
}