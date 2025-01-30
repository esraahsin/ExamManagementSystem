package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Validation;
import com.example.demo.model.ValidationStatus;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Integer> {
    // Basic CRUD methods inherited from JpaRepository
    
    // Find by exam ID
    List<Validation> findByExamId(int examId);
    
    // Find by validator ID
    List<Validation> findByValidatedById(int validatorId);
    
    // Find exam validations by status
    @Query("SELECT v FROM Validation v " +
           "WHERE v.exam.id = :examId " +
           "AND v.status = :status " +
           "ORDER BY v.validationDate DESC")
    List<Validation> findExamValidations(
        @Param("examId") int examId,
        @Param("status") ValidationStatus status
    );
    
    // Find pending validations by department
    @Query("SELECT v FROM Validation v " +
           "WHERE v.exam.department.id = :deptId " +
           "AND v.status = 'PENDING' " +
           "ORDER BY v.validationDate ASC")
    List<Validation> findPendingValidationsByDepartment(@Param("deptId") int departmentId);
    
    // Find validations by date range
    @Query("SELECT v FROM Validation v " +
           "WHERE v.validationDate BETWEEN :startDate AND :endDate " +
           "ORDER BY v.validationDate DESC")
    List<Validation> findValidationsByDateRange(
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate
    );
}
