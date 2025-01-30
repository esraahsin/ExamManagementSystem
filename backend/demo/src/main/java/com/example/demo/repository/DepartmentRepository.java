package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // Basic finder methods
    Optional<Department> findByName(String name);
    
    List<Department> findByIsActive(boolean isActive);
    
    boolean existsByName(String name);
    
    // Custom queries
    @Query("SELECT d FROM Department d WHERE d.head.id = :userId")
    Optional<Department> findByHeadId(@Param("userId") Long userId);
    
    @Query("SELECT DISTINCT d FROM Department d " +
           "LEFT JOIN FETCH d.head " +
           "WHERE d.isActive = true")
    List<Department> findAllActiveDepartmentsWithHead();
    
    // Search departments
    @Query("SELECT d FROM Department d WHERE " +
           "LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Department> searchDepartments(@Param("searchTerm") String searchTerm);
    
    // Department statistics
    @Query("SELECT d FROM Department d WHERE " +
           "(SELECT COUNT(u) FROM User u WHERE u.department = d AND u.role = 'STUDENT') > :minStudents")
    List<Department> findDepartmentsWithMinimumStudents(@Param("minStudents") long minStudents);
    
    // Department exam statistics
    @Query("SELECT d.id, d.name, COUNT(e) as examCount " +
           "FROM Department d LEFT JOIN d.exams e " +
           "GROUP BY d.id, d.name")
    List<Object[]> getDepartmentExamStatistics();
}
