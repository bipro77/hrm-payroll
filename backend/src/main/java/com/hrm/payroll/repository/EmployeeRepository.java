package com.hrm.payroll.repository;

import com.hrm.payroll.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Employee entity.
 *
 * Responsibilities:
 * - Provides CRUD operations via JpaRepository
 * - Handles employee-related database queries
 *
 * Notes:
 * - Spring Data JPA auto-generates implementations for derived query methods
 * - Optimized with indexing (e.g., email, department, salary if indexed)
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find employee by email (unique field).
     *
     * Use Case:
     * - Employee lookup
     * - Prevent duplicate registration
     *
     * Performance:
     * - Optimized if 'email' column is indexed/unique
     *
     * @param email employee email
     * @return Optional<Employee>
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Check if employee exists by email.
     *
     * Use Case:
     * - Validation before create (avoid duplicates)
     *
     * Performance:
     * - Faster than fetching full entity (uses EXISTS query)
     *
     * @param email employee email
     * @return true if exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find employees by department.
     *
     * Use Case:
     * - Department-wise listing (HR dashboard)
     *
     * @param department department name
     * @return list of employees
     */
    List<Employee> findByDepartment(String department);

    /**
     * Find employees with salary greater than given value.
     *
     * Use Case:
     * - Payroll filtering
     * - High-salary reporting
     *
     * @param salary minimum salary
     * @return list of employees
     */
    List<Employee> findBySalaryGreaterThan(Double salary);

    /**
     * Find employees by department with pagination.
     *
     * Use Case:
     * - Large dataset handling (UI table / dashboard)
     *
     * Performance:
     * - Prevents loading entire table into memory
     *
     * @param department department name
     * @param pageable pagination + sorting config
     * @return paginated employee result
     */
    Page<Employee> findByDepartment(String department, Pageable pageable);

    /**
     * Find employees by department and salary threshold.
     *
     * Use Case:
     * - Advanced filtering (HR analytics)
     *
     * @param department department name
     * @param salary minimum salary
     * @return filtered employee list
     */
    List<Employee> findByDepartmentAndSalaryGreaterThan(String department, Double salary);
}