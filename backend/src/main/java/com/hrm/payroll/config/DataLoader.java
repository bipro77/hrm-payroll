package com.hrm.payroll.config;

import com.hrm.payroll.entity.Role;
import com.hrm.payroll.entity.User;
import com.hrm.payroll.entity.Employee;
import com.hrm.payroll.repository.UserRepository;
import com.hrm.payroll.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {

            // ===== USER SEED =====
            if (!userRepository.existsByUsername("admin")) {

                User admin = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(admin);
            }

            // ===== EMPLOYEE SEED =====
            if (employeeRepository.count() == 0) {

                List<Employee> employees = List.of(

                        Employee.builder()
                                .department("IT")
                                .designation("Software Engineer")
                                .email("rahim@example.com")
                                .name("Rahim Uddin")
                                .salary(BigDecimal.valueOf(50000))
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build(),

                        Employee.builder()
                                .department("HR")
                                .designation("HR Manager")
                                .email("karim@example.com")
                                .name("Karim Hasan")
                                .salary(BigDecimal.valueOf(45000))
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build(),

                        Employee.builder()
                                .department("Finance")
                                .designation("Accountant")
                                .email("salma@example.com")
                                .name("Salma Akter")
                                .salary(BigDecimal.valueOf(40000))
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build(),

                        Employee.builder()
                                .department("IT")
                                .designation("Backend Developer")
                                .email("tanvir@example.com")
                                .name("Tanvir Ahmed")
                                .salary(BigDecimal.valueOf(55000))
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build(),

                        Employee.builder()
                                .department("Admin")
                                .designation("Office Assistant")
                                .email("mina@example.com")
                                .name("Mina Begum")
                                .salary(BigDecimal.valueOf(30000))
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build()
                );

                employeeRepository.saveAll(employees);
            }
        };
    }
}