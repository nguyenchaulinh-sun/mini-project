package com.example.employee_management.controller;

import com.example.employee_management.service.UtilityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utility")
public class UtilityController {

    private final UtilityService utilityService;

    public UtilityController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @GetMapping("/generate-code")
    public String generateCode() {
        return utilityService.generateEmployeeCode();
    }

    @GetMapping("/format-name")
    public String formatName(@RequestParam String name) {
        return utilityService.formatName(name);
    }
}