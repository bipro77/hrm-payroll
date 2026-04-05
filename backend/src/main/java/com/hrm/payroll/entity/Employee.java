package com.hrm.payroll.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents an employee profile in the HR domain.
 * Responsibilities:
 * - Stores business-specific employee data
 * - Acts as the inverse side of the User association
 * Design:
 * - Decoupled from authentication concerns (handled by User entity)
 * - Supports optional linkage to a system user account
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Employee full name.
     * Required for identification in business workflows.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Unique business identifier for communication and lookup.
     * Enforced at database level to prevent duplication.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Organizational grouping (e.g., HR, IT).
     */
    private String department;

    /**
     * Job title or role within the organization.
     */
    private String designation;

    /**
     * Base compensation.
     * Uses BigDecimal to ensure financial precision.
     */
    private BigDecimal salary;

    /**
     * Inverse side of User association.
     * - Mapped by 'employee' field in User entity
     * - No foreign key maintained in this table
     * - Lazy-loaded to avoid unnecessary joins
     * Serialization:
     * - Ignored to prevent circular references in JSON
     */
    @JsonIgnore
    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY)
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}