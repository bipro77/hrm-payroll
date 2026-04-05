package com.hrm.payroll.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class RegisterRequest {

    @NotBlank
    @Size(min = 4, max = 50)
    private String username;

    @NotBlank
    @Size(min = 3)
    private String password;
    @NotBlank
    @Email
    private String email;


    @NotBlank
    private String role;

}