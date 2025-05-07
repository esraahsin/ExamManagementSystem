package com.example.demo.repository;
import com.example.demo.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import javax.management.relation.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    long countByRole(UserRole role);

    
    User  findByUserId(int userId);

    // Check if email exists
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    

    // Find active users
    List<User> findByIsActive(boolean isActive);

    List<User> findByDepartment_DepartmentId(int departmentId);
    List<User> findByRoleAndDepartment_DepartmentId(UserRole role, int departmentId);
    // Find users by role and active status
    List<User> findByRoleAndIsActive(UserRole role, boolean isActive);

    List<User> findByRole(UserRole role); // Filtrer uniquement les enseignants
}



