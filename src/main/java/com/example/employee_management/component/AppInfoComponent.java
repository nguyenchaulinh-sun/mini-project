package com.example.employee_management.component;

import org.springframework.stereotype.Component;

@Component
public class AppInfoComponent {

    public String getApplicationName() {
        return "Employee Management System";
    }

    public String getModule() {
        return "Module 2 - Custom Bean & IoC";
    }
}