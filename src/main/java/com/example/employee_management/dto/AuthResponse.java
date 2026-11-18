package com.example.employee_management.dto;

public class AuthResponse {

    private String message;
    private String username;
    private String role;
    private String token;
    private String tokenType;

    public AuthResponse(String message, String username,
                       String role, String token) {
        this.message = message;
        this.username = username;
        this.role = role;
        this.token = token;
        this.tokenType = "Bearer";
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}