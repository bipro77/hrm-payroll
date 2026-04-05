package com.hrm.payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(scanBasePackages = "com.hrm")
public class HrmPayrollApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmPayrollApplication.class, args);
	}

}