package com.hrm.payroll.config;

import com.hrm.payroll.entity.Role;
import com.hrm.payroll.entity.User;
import com.hrm.payroll.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {

                User admin = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN)   //
                        .build();

                userRepository.save(admin);
            }
        };
    }
}