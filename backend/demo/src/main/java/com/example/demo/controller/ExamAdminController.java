package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ExamDTO;
import com.example.demo.model.Exam;
import com.example.demo.service.ExamService;
@RestController
@RequestMapping("/api/admin/exams")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamAdminController {
    
    @Autowired
    private ExamService examService;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        try {
            List<ExamDTO> exams = examService.getAllExams();
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            System.err.println("Error in getAllExams: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<ExamDTO> addExam(@RequestBody ExamDTO examDTO) {
        try {
            ExamDTO createdExam = examService.createExam(examDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExam);
        } catch (Exception e) {
            System.err.println("Error in addExam: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}