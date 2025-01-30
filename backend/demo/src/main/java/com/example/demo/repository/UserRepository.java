package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
import com.example.demo.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Basic finder methods
    Optional<User> findByEmail(String email);
    
    List<User> findByDepartmentId(int departmentId);
    
    List<User> findByRole(UserRole role);
    
    boolean existsByEmail(String email);
    
    // Custom queries
    @Query("SELECT u FROM User u WHERE u.department.id = :departmentId AND u.role = :role")
    List<User> findByDepartmentAndRole(
        @Param("departmentId") Long departmentId, 
        @Param("role") UserRole role
    );
    
    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.role = :role")
    List<User> findActiveUsersByRole(@Param("role") UserRole role);
    
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);
    
    // Query for invigilators
    @Query("SELECT u FROM User u WHERE u.role = 'TEACHER' AND u.isActive = true " +
           "AND u.id NOT IN (SELECT i.user.id FROM Invigilator i WHERE i.exam.id = :examId)")
    List<User> findAvailableInvigilatorsForExam(@Param("examId") int examId);
    
    // Query for department statistics
    @Query("SELECT COUNT(u) FROM User u WHERE u.department.id = :departmentId AND u.role = :role")
    long countByDepartmentAndRole(
        @Param("departmentId") int departmentId, 
        @Param("role") UserRole role
    );
}
