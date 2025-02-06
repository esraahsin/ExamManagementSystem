package com.example.demo.controller;

import com.example.demo.model.Exam;
import com.example.demo.model.User;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/exams")
    public ResponseEntity<List<Exam>> getStudentExams(@RequestParam String email) {
        // Find the student by email
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Get exams based on the student's specialty
        List<Exam> exams = examRepository.findBySpeciality(student.getSpeciality());
        return ResponseEntity.ok(exams);
    }
}
