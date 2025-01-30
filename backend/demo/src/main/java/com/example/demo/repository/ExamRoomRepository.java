package com.example.demo.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ExamRoom;

@Repository
public interface ExamRoomRepository extends JpaRepository<ExamRoom, Integer> {
    
    @Query("SELECT er FROM ExamRoom er WHERE er.room.id = :roomId " +
           "AND er.exam.examDate = :date " +
           "AND ((er.exam.startTime <= :endTime AND er.exam.endTime >= :startTime))")
    List<ExamRoom> findConflictingReservations(
        @Param("roomId") Long roomId,
        @Param("date") Date date,
        @Param("startTime") Time startTime,
        @Param("endTime") Time endTime
    );
}
