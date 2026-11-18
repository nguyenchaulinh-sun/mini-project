package com.example.employee_management.controller;

import com.example.employee_management.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/employees/count")
    public String getTotalEmployees() {
        return "Total Employees: " + reportService.getTotalEmployees();
    }
}