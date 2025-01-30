package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Exam;
import com.example.demo.model.User;
import com.example.demo.model.Validation;
import com.example.demo.model.ValidationStatus;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.ValidationRepository;

import jakarta.transaction.Transactional;

@Service
public class ValidationService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationRepository validationRepository;

    // Validate an exam schedule
    @Transactional
    public Validation validateExam(int examId, int validatedByUserId, String comments) {
        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        User validatedBy = userRepository.findById(validatedByUserId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + validatedByUserId));

        Validation validation = new Validation();
        validation.setExam(exam);
        validation.setValidatedBy(validatedBy);
        validation.setStatus(ValidationStatus.VALIDATED);
        validation.setComments(comments);
        validation.setValidationDate(LocalDate.now()); // Use LocalDate to match the model

        return validationRepository.save(validation);
    }

    // Reject an exam schedule
    @Transactional
    public Validation rejectExam(int examId, int validatedByUserId, String comments) {
        Exam exam = examRepository.findById(examId)
            .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + examId));

        User validatedBy = userRepository.findById(validatedByUserId)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + validatedByUserId));

        Validation validation = new Validation();
        validation.setExam(exam);
        validation.setValidatedBy(validatedBy);
        validation.setStatus(ValidationStatus.REJECTED);
        validation.setComments(comments);
        validation.setValidationDate(LocalDate.now()); // Use LocalDate to match the model

        return validationRepository.save(validation);
    }
}