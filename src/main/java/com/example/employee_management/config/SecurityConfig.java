package com.example.employee_management.config;

import com.example.employee_management.security.CustomUserDetailsService;
import com.example.employee_management.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ================= API SECURITY =================

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .securityMatcher("/api/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth

                // Public authentication APIs
                .requestMatchers("/api/auth/**").permitAll()

                // USER and ADMIN can access statistics APIs
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/employees/statistics/**"
                ).hasAnyRole("USER", "ADMIN")

                // USER and ADMIN can view employee list
                .requestMatchers(HttpMethod.GET, "/api/employees")
                    .hasAnyRole("USER", "ADMIN")

                // Admin only for remaining employee API operations
                .requestMatchers("/api/employees/**")
                    .hasRole("ADMIN")

                // Other APIs require authentication
                .anyRequest().authenticated()
            )
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // ================= WEB SECURITY =================

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // Public pages
                .requestMatchers(
                    "/login",
                    "/register",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico"
                ).permitAll()

                // Registration submission
                .requestMatchers(HttpMethod.POST, "/register").permitAll()

                // USER and ADMIN can view employee list
                .requestMatchers(HttpMethod.GET, "/employees/list")
                    .hasAnyRole("USER", "ADMIN")

                // Search is read-only
                .requestMatchers(HttpMethod.GET, "/employees/search")
                    .hasAnyRole("USER", "ADMIN")

                // Statistics: USER and ADMIN can access
                .requestMatchers("/employees/statistics", "/employees/statistics/**")
                    .hasAnyRole("USER", "ADMIN")

                // All remaining employee management pages are ADMIN-only
                .requestMatchers("/employees/**")
                    .hasRole("ADMIN")

                // Any other page requires login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/employees/list", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        // CSRF remains enabled for browser forms
        return http.build();
    }
}