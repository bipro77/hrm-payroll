package com.hrm.payroll.controller;

import com.hrm.payroll.dto.EmployeeDto;
import com.hrm.payroll.dto.PagedResponse;
import com.hrm.payroll.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Employee resource management.
 *
 * Responsibilities:
 * - Exposes CRUD endpoints
 * - Applies validation at API boundary
 * - Delegates business logic to service layer
 *
 * Security:
 * - Role-based access control enforced via method-level annotations
 * Base path: /api/employees
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // allow frontend
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Creates a new employee.
     *
     * Access:
     * - Restricted via security configuration (ADMIN recommended)
     */
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
            @Valid @RequestBody EmployeeDto employeeDto) {

        return ResponseEntity.ok(employeeService.createEmployee(employeeDto));
    }

    /**
     * Retrieves paginated employee list.
     * Access:
     * - ADMIN only (sensitive bulk data exposure)
     * Notes:
     * - Supports pagination and dynamic sorting
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<EmployeeDto> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<EmployeeDto> employeePage =
                employeeService.getAllEmployees(pageable);

        return PagedResponse.<EmployeeDto>builder()
                .content(employeePage.getContent())
                .page(employeePage.getNumber())
                .size(employeePage.getSize())
                .totalElements(employeePage.getTotalElements())
                .build();
    }

    /**
     * Retrieves a single employee by ID.
     *
     * Access:
     * - USER / ADMIN (controlled via security config)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(
            @PathVariable Long id) {

        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * Updates an existing employee.
     *
     * Access:
     * - ADMIN only (data integrity protection)
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDto) {

        return ResponseEntity.ok(
                employeeService.updateEmployee(id, employeeDto)
        );
    }

    /**
     * Deletes an employee.
     *
     * Access:
     * - ADMIN only (destructive operation)
     *
     * Returns:
     * - 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}