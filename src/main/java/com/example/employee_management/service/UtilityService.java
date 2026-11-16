package com.example.employee_management.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class UtilityService {

    private final AtomicLong counter = new AtomicLong(0);

    public String generateEmployeeCode() {
        return String.format("EMP%04d", counter.incrementAndGet());
    }

    public String formatName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Employee name cannot be empty"
            );
        }

        return name.trim().toUpperCase();
    }
}