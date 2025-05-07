package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    long countByRole(UserRole role);

    // Find user by email
    Optional<User> findByEmail(String email);
    User  findByUserId(int userId);

    // Check if email exists
    boolean existsByEmail(String email);

    // Find users by role
    List<User> findByRole(UserRole role);

    // Find users by department

    // Find active users
    List<User> findByIsActive(boolean isActive);

    List<User> findByDepartment_DepartmentId(int departmentId);
    List<User> findByRoleAndDepartment_DepartmentId(UserRole role, int departmentId);
    // Find users by role and active status
    List<User> findByRoleAndIsActive(UserRole role, boolean isActive);

}