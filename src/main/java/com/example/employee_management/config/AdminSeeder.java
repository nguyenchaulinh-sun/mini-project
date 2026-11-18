package com.example.employee_management.config;

import com.example.employee_management.entity.User;
import com.example.employee_management.enums.Role;
import com.example.employee_management.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            String username = System.getenv("ADMIN_USERNAME");
            String password = System.getenv("ADMIN_PASSWORD");

            // Skip seeding if environment variables are not configured
            if (username == null || username.isBlank()
                    || password == null || password.isBlank()) {
                return;
            }

            // Create admin only if the username does not exist
            if (!userRepository.existsByUsername(username)) {

                User admin = new User();
                admin.setUsername(username);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Admin account initialized.");
            }
        };
    }
}