package com.example.demo.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Invigilator;

@Repository
public interface InvigilatorRepository extends JpaRepository<Invigilator, Integer> {
    List<Invigilator> findByUserId(int userId);
    
    List<Invigilator> findByExamId(int examId);
    
    @Query("SELECT i FROM Invigilator i WHERE i.user.id = :userId " +
           "AND i.exam.examDate = :date " +
           "AND ((i.exam.startTime <= :endTime AND i.exam.endTime >= :startTime))")
    List<Invigilator> findConflictingAssignments(
        @Param("userId") int userId,
        @Param("date") Date date,
        @Param("startTime") Time startTime,
        @Param("endTime") Time endTime
    );
}
