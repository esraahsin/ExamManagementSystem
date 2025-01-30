package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ScheduleLog;
import com.example.demo.model.User;
import com.example.demo.repository.ScheduleLogRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ScheduleLogService {

    @Autowired
    private ScheduleLogRepository scheduleLogRepository;
    private UserRepository userRepository ;
    // Log a change to the exam schedule
    @Transactional
    public ScheduleLog logChange(String action, String description, int performedBy) {
        ScheduleLog log = new ScheduleLog();
        log.setAction(action);
        log.setDescription(description);
        User user = userRepository.findById(performedBy)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + performedBy));
        log.setPerformedBy(user);
        log.setTimestamp(LocalDateTime.now());

        return scheduleLogRepository.save(log);
    }

    // Get all logs
    public List<ScheduleLog> getAllLogs() {
        return scheduleLogRepository.findAll();
    }
}