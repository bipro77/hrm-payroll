package com.hrm.payroll.service;

public interface JwtService {

    String generateToken(String username, String role);

    String extractUsername(String token);

    String extractRole(String token);

    // VALIDATION (SECURE VERSION)

    boolean validateToken(String token, String username);
}