package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.example.demo.model.Invigilator;
import com.example.demo.model.User;

@Repository
public interface InvigilatorRepository extends JpaRepository<Invigilator, Integer> {

    @Query("SELECT COUNT(i) > 0 FROM Invigilator i " +
           "JOIN i.exam e " +
           "WHERE i.user.userId = :userId " +
           "AND e.examDate = :examDate " +
           "AND ((e.startTime BETWEEN :startTime AND :endTime) " +
           "OR (e.endTime BETWEEN :startTime AND :endTime) " +
           "OR (:startTime BETWEEN e.startTime AND e.endTime))")
    boolean isTeacherOccupied(@Param("userId") Integer userId,
                              @Param("examDate") Date examDate,
                              @Param("startTime") Time startTime,
                              @Param("endTime") Time endTime);
    
    @Query("SELECT i FROM Invigilator i WHERE i.user.userId = :userId")
    List<Invigilator> findByUserId(@Param("userId") int userId);

    // Change findByTeacher to findByUser
    List<Invigilator> findByUser(User user);
}
