package com.example.demo.controller;

import com.example.demo.model.UserRole;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*")
public class StatsController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamRepository examRepository;

    @GetMapping
    public ResponseEntity<StatsDTO> getStatistics() {
        long departmentCount = departmentRepository.count();
        long studentCount = userRepository.countByRole(UserRole.ETUDIANT);
        long examCount = examRepository.count();
        
        return ResponseEntity.ok(new StatsDTO(departmentCount, studentCount, examCount));
    }

    // DTO class
    private static record StatsDTO(
        long departments,
        long students,
        long exams
    ) {}
}