package com.hrm.payroll.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents an authenticated system user.
 *
 * Responsibilities:
 * - Stores credentials and role for authorization
 * - Owns association with Employee profile (optional)
 *
 * Design:
 * - One-to-one ownership with Employee (FK in users table)
 * - Supports role-based access control (RBAC)
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "users",
        indexes = {

                // Optimizes authentication lookup (findByUsername)
                @Index(name = "idx_username", columnList = "username"),

                // Improves join performance with Employee
                @Index(name = "idx_employee_id", columnList = "employee_id")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier for authentication.
     * Indexed for fast lookup during login.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Hashed password (BCrypt or stronger).
     * Excluded from API responses for security.
     */
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    //@Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Role used for authorization (e.g., ROLE_ADMIN, ROLE_USER).
     * Stored as string to ensure stability across enum changes.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    /**
     * Optional link to Employee profile.
     * - Owner side of the relationship (FK: employee_id)
     * - Enforces one-to-one mapping via unique constraint
     * - Lazy loading to avoid unnecessary joins
     */
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    /**
     * Controls authentication eligibility.
     * Used to disable login without deleting user.
     */
    @Column(nullable = false)
    private boolean enabled = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Business-level active flag (independent of authentication).
     * Useful for soft-deactivation in domain logic.
     */
    private boolean active = true;
}