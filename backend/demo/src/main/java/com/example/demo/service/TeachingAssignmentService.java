package com.example.demo.service;

import com.example.demo.model.TeachingAssignment;
import com.example.demo.repository.TeachingAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingAssignmentService {

    @Autowired
    private TeachingAssignmentRepository teachingAssignmentRepository;

    public List<TeachingAssignment> getAllAssignments() {
        return teachingAssignmentRepository.findAll();
    }

    public TeachingAssignment assignSubjectToTeacher(TeachingAssignment teachingAssignment) {
        return teachingAssignmentRepository.save(teachingAssignment);
    }
}
