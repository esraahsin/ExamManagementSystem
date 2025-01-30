package com.example.demo.service;

import com.example.demo.dto.ExamDTO;
import com.example.demo.model.Exam;
import com.example.demo.model.Department;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all exams and convert them to DTOs
    public List<ExamDTO> getAllExams() {
        return examRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // Create a new exam from a DTO
    public ExamDTO createExam(ExamDTO examDTO) {
    // Validate examId
    if (examDTO.getExamId() == null) {
        throw new IllegalArgumentException("Exam ID must be provided.");
    }
    if (examDTO.getExamId() <= 0) {
        throw new IllegalArgumentException("Exam ID must be a positive integer.");
    }

    // Check for duplicate examId
    if (examRepository.existsById(examDTO.getExamId())) {
        throw new DataIntegrityViolationException("Exam ID " + examDTO.getExamId() + " already exists.");
    }

    // Proceed to save
    Exam exam = convertToEntity(examDTO);
    Exam savedExam = examRepository.save(exam);
    return convertToDTO(savedExam);
}

    // Convert Exam entity to ExamDTO
    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setExamId(exam.getExamId());
        dto.setSubject(exam.getSubject());

        // Convert to LocalDate and LocalTime
        dto.setExamDate(exam.getExamDate().toLocalDate());
        dto.setStartTime(exam.getStartTime().toLocalTime());
        dto.setEndTime(exam.getEndTime().toLocalTime());

        dto.setDifficulty(exam.getDifficulty());
        dto.setCoefficient(exam.getCoefficient());

        // Check if department exists
        dto.setDepartmentName(exam.getDepartment() != null ? 
            exam.getDepartment().getName() : null);

        dto.setIsDuplicate(exam.isDuplicate());

        return dto;
    }

    // Convert ExamDTO to Exam entity
    private Exam convertToEntity(ExamDTO examDTO) {
        Exam exam = new Exam();
        exam.setExamId(examDTO.getExamId()); // <-- ADD THIS LINE

        exam.setSubject(examDTO.getSubject());

        // Convert LocalDate and LocalTime to java.sql.Date and java.sql.Time
        exam.setExamDate(java.sql.Date.valueOf(examDTO.getExamDate()));
        exam.setStartTime(java.sql.Time.valueOf(examDTO.getStartTime()));
        exam.setEndTime(java.sql.Time.valueOf(examDTO.getEndTime()));

        exam.setDifficulty(examDTO.getDifficulty());
        exam.setCoefficient(examDTO.getCoefficient());
        exam.setDuplicate(examDTO.getIsDuplicate());

        // Set the department if provided
        if (examDTO.getDepartmentName() != null) {
            Department department = departmentRepository.findByName(examDTO.getDepartmentName());
            exam.setDepartment(department);

        }

        return exam;
    }
}
