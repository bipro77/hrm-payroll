package com.hrm.payroll.service.impl;

import com.hrm.payroll.dto.EmployeeDto;
import com.hrm.payroll.entity.Employee;
import com.hrm.payroll.exception.ResourceNotFoundException;
import com.hrm.payroll.repository.EmployeeRepository;
import com.hrm.payroll.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    // ==============================
    // ✅ CREATE
    // ==============================

    @Override
    public EmployeeDto createEmployee(EmployeeDto dto) {

        Employee employee = mapToEntity(dto);
        Employee saved = employeeRepository.save(employee);

        return mapToDto(saved);
    }

    // ==============================
    // 📄 READ ALL (PAGINATION)
    // ==============================

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> getAllEmployees(Pageable pageable) {

        return employeeRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    // ==============================
    // 🔍 READ ONE
    // ==============================

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "id", id));
    }

    // ==============================
    // 🔄 UPDATE
    // ==============================

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee", "id", id));

        updateEntity(employee, dto);

        return mapToDto(employeeRepository.save(employee));
    }

    // ==============================
    // ❌ DELETE
    // ==============================

    @Override
    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee", "id", id);
        }

        employeeRepository.deleteById(id);
    }

    // ==============================
    // 🔁 MAPPING METHODS
    // ==============================

    private Employee mapToEntity(EmployeeDto dto) {
        return Employee.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .department(dto.getDepartment())
                .designation(dto.getDesignation())
                .salary(dto.getSalary())
                .build();
    }

    private EmployeeDto mapToDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .build();
    }

    private void updateEntity(Employee employee, EmployeeDto dto) {
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setDepartment(dto.getDepartment());
        employee.setDesignation(dto.getDesignation());
        employee.setSalary(dto.getSalary());
    }
}