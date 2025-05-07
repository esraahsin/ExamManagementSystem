package com.example.demo.controller;

import com.example.demo.model.TeachingAssignment;
import com.example.demo.service.TeachingAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class TeachingAssignmentController {

    @Autowired
    private TeachingAssignmentService teachingAssignmentService;

    // Récupérer toutes les affectations
    @GetMapping
    public List<TeachingAssignment> getAllAssignments() {
        return teachingAssignmentService.getAllAssignments();
    }

    // Ajouter une nouvelle affectation
    @PostMapping
    public TeachingAssignment assignSubjectToTeacher(@RequestBody TeachingAssignment teachingAssignment) {
        return teachingAssignmentService.assignSubjectToTeacher(teachingAssignment);
    }
}
