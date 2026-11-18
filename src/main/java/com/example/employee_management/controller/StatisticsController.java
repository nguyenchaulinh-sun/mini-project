
package com.example.employee_management.controller;

import com.example.employee_management.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/employees/statistics")
    public String showStatistics(Model model) {

        model.addAttribute(
                "totalEmployees",
                statisticsService.getTotalEmployees()
        );

        model.addAttribute(
                "departmentStatistics",
                statisticsService.getEmployeeCountByDepartment()
        );

        return "employees/statistics";
    }
}
