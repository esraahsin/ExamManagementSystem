package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ExamDTO;
import com.example.demo.model.Exam;

import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
import com.example.demo.service.ExamService;
@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamPublicController {
    
    @Autowired
    private ExamService examService;
     @Autowired
    private TeachingAssignmentRepository teachingAssignmentRepository;
    @Autowired
    private ExamRepository examRepository;
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
   
    @GetMapping("/{id}")
public ResponseEntity<?> getExam(@PathVariable("id") String id) {
    try {
        Integer examId = Integer.parseInt(id); // Essaye de convertir l'ID en Integer
        Exam exam = examRepository.findById(examId).orElse(null);
        if (exam == null) {
            return ResponseEntity.badRequest().body("Examen non trouvé");
        }
        return ResponseEntity.ok(exam);
    } catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body("ID invalide, doit être un nombre");
    }
}

    
   

}

