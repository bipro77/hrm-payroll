package com.hrm.payroll.repository;

import com.hrm.payroll.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity.
 *
 * Responsibilities:
 * - Provides CRUD operations via JpaRepository
 * - Handles database interaction for User authentication and management
 *
 * Notes:
 * - Spring Data JPA automatically generates query implementations
 * - No need to write SQL/JPQL for basic operations
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by username.
     *
     * Use Case:
     * - Authentication (login process)
     * - JWT token generation (fetch user details)
     *
     * Performance:
     * - Optimized using DB index on 'username' column
     *
     * Return Type:
     * - Optional<User> to safely handle absence of user
     *   (avoids NullPointerException)
     *
     * @param username unique username
     * @return Optional containing User if found, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks whether a user exists by username.
     *
     * Use Cases:
     * - Validation during registration (prevent duplicate usernames)
     * - Quick existence check before authentication or user creation
     *
     * Performance:
     * - Executes an optimized EXISTS query at the database level
     * - Does NOT fetch full entity → faster than findByUsername()
     * - Utilizes index on 'username' column (idx_username)
     *
     * Behavior:
     * - Returns true if a user with given username exists
     * - Returns false if no such user is found
     *
     * @param username unique username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);

}