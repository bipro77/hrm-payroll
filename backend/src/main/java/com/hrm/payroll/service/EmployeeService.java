package com.hrm.payroll.service;

import com.hrm.payroll.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto dto);

    Page<EmployeeDto> getAllEmployees(Pageable pageable); //

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto updateEmployee(Long id, EmployeeDto dto);

    void deleteEmployee(Long id);
}