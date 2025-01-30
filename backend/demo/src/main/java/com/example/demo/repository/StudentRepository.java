package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    // Basic CRUD methods inherited from JpaRepository
    
    // Find by program
    List<Student> findByProgram(String program);
    
    // Find students registered for an exam
    @Query("SELECT s FROM Student s JOIN s.examStudents es WHERE es.exam.id = :examId")
    List<Student> findByExamId(@Param("examId") int examId);
    
    // Find by department
    List<Student> findByDepartmentId(int departmentId);
    
    // Find by enrollment year
    List<Student> findByEnrollmentYear(Integer year);
    
    // Search students by name or ID
    @Query("SELECT s FROM Student s " +
           "WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR s.studentId LIKE CONCAT('%', :searchTerm, '%')")
    List<Student> searchStudents(@Param("searchTerm") String searchTerm);
}
