package com.hrm.payroll.service;

import com.hrm.payroll.dto.AuthRequest;
import com.hrm.payroll.dto.AuthResponse;
import com.hrm.payroll.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    String register(RegisterRequest request);
}