package com.example.demo.repository;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.*;;

@Repository
public interface ExamStudentRepository extends JpaRepository<ExamStudent, Integer> {
    // Basic CRUD methods are inherited from JpaRepository
    
    // Find by exam ID
    List<ExamStudent> findByExamId(int examId);
    
    // Find by student ID
    List<ExamStudent> findByStudentId(int studentId);
    
    // Find by exam ID and status
    List<ExamStudent> findByExamIdAndStatus(int examId, String status);
    
    // Find upcoming exams for a student
    @Query("SELECT es FROM ExamStudent es " +
           "WHERE es.student.id = :studentId " +
           "AND es.exam.examDate >= :date " +
           "ORDER BY es.exam.examDate ASC")
    List<ExamStudent> findUpcomingExamsForStudent(
        @Param("studentId") int studentId,
        @Param("date") Date date
    );
    
    // Find students registered for specific exam and status
    @Query("SELECT es FROM ExamStudent es " +
           "WHERE es.exam.id = :examId " +
           "AND es.status = :status")
    List<ExamStudent> findStudentsByExamAndStatus(
        @Param("examId") int examId,
        @Param("status") String status
    );
}
