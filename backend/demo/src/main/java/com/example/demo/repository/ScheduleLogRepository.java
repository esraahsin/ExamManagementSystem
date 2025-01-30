package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ScheduleLog;

@Repository
public interface ScheduleLogRepository extends JpaRepository<ScheduleLog, Integer> {
   
}