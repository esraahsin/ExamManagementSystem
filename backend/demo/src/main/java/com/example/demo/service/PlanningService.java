package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Exam;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PlanningService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    // Generate the exam schedule
    @Transactional
    public List<Exam> generateSchedule() {
        // Logic to generate the schedule based on constraints
        // (e.g., room availability, invigilator availability)
        return examRepository.findAll(); // Placeholder
    }
}