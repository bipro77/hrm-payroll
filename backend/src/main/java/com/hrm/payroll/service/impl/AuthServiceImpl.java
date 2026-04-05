package com.hrm.payroll.service.impl;

import com.hrm.payroll.dto.AuthRequest;
import com.hrm.payroll.dto.AuthResponse;
import com.hrm.payroll.dto.RegisterRequest;
import com.hrm.payroll.entity.Role;
import com.hrm.payroll.entity.User;
import com.hrm.payroll.repository.UserRepository;
import com.hrm.payroll.service.AuthService;
import com.hrm.payroll.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // LOGIN
    @Override
    public AuthResponse login(AuthRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            log.warn("Invalid login attempt: {}", request.getUsername());
            throw new IllegalArgumentException("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }

    // REGISTER
    @Override
    public String register(RegisterRequest request) {

        // 🔒 Username duplicate
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 🔐 Role validation
        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        // 🔐 Get current user role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentUserRole = null;

        if (authentication != null && authentication.isAuthenticated()
                && authentication.getAuthorities() != null) {

            currentUserRole = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(a -> a.getAuthority())
                    .orElse(null);
        }

        // 🚫 Only ADMIN can create ADMIN
        if (role == Role.ADMIN) {
            if (currentUserRole == null || !currentUserRole.equals("ROLE_ADMIN")) {
                throw new AccessDeniedException("Only admin can create admin");
            }
        }

        // 👤 Save user
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        log.info("User registered: {}", user.getUsername());

        return "User registered successfully";
    }
}