package com.hrm.payroll.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO for transferring Employee data.
 *
 * Used for:
 * - API request/response
 * - Validation layer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Size(max = 150, message = "Email too long")
    private String email;

    @Size(max = 50)
    private String department;

    @Size(max = 50)
    private String designation;

    @Positive(message = "Salary must be positive")
    private BigDecimal salary;
}