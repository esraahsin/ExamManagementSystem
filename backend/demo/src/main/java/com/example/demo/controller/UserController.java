package com.example.demo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Exam;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/users/teachers")  // Changer la route
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExamRepository examRepository;

    @GetMapping
    public List<User> getAllTeachers() {
        return userRepository.findByRole(UserRole.TEACHER);
    }
     @GetMapping("/{teacherId}/schedule")
    public List<Exam> getTeacherExamSchedule(@PathVariable("teacherId") int teacherId) {
        return examRepository.findExamsByInvigilatorId(teacherId);
    }
}


