package com.example.demo.service;

import com.example.demo.dto.ExamDTO;
import com.example.demo.model.Exam;
import com.example.demo.model.TeachingAssignment;
import com.example.demo.model.Department;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.TeachingAssignmentRepository;
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
private ExamDTO convertToDTO(Exam exam) {
    ExamDTO dto = new ExamDTO();
    dto.setExamId(exam.getExamId());
    dto.setSubject(exam.getSubject());
    dto.setExamDate(exam.getExamDate().toLocalDate());
    dto.setStartTime(exam.getStartTime().toLocalTime());
    dto.setEndTime(exam.getEndTime().toLocalTime());
    dto.setDifficulty(exam.getDifficulty());
    dto.setCoefficient(exam.getCoefficient());
    dto.setDepartmentName(exam.getDepartment() != null ? exam.getDepartment().getName() : null);
    dto.setIsDuplicate(exam.isDuplicate());
    dto.setSpeciality(exam.getSpeciality()); // Nouvel attribut
    return dto;
}

private Exam convertToEntity(ExamDTO examDTO) {
    Exam exam = new Exam();
    exam.setExamId(examDTO.getExamId());
    exam.setSubject(examDTO.getSubject());
    exam.setExamDate(java.sql.Date.valueOf(examDTO.getExamDate()));
    exam.setStartTime(java.sql.Time.valueOf(examDTO.getStartTime()));
    exam.setEndTime(java.sql.Time.valueOf(examDTO.getEndTime()));
    exam.setDifficulty(examDTO.getDifficulty());
    exam.setCoefficient(examDTO.getCoefficient());
    exam.setDuplicate(examDTO.getIsDuplicate());
    exam.setSpeciality(examDTO.getSpeciality()); // Nouvel attribut
    return exam;
}
    public boolean deleteExam(Integer id) {
        if (examRepository.existsById(id)) {
            examRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ExamDTO updateExam(Integer id, ExamDTO examDTO) {
        Optional<Exam> optionalExam = examRepository.findById(id);
        if (optionalExam.isPresent()) {
            Exam existingExam = optionalExam.get();

            // Mettre à jour les champs de l'examen existant
            existingExam.setSubject(examDTO.getSubject());
            existingExam.setExamDate(java.sql.Date.valueOf(examDTO.getExamDate()));
            existingExam.setStartTime(java.sql.Time.valueOf(examDTO.getStartTime()));
            existingExam.setEndTime(java.sql.Time.valueOf(examDTO.getEndTime()));
            existingExam.setDifficulty(examDTO.getDifficulty());
            existingExam.setCoefficient(examDTO.getCoefficient());
            existingExam.setDuplicate(examDTO.getIsDuplicate());

            // Mettre à jour le département si nécessaire
            if (examDTO.getDepartmentName() != null) {
                Department department = departmentRepository.findByName(examDTO.getDepartmentName());
                if (department != null) {
                    existingExam.setDepartment(department);
                }
            }

            Exam updatedExam = examRepository.save(existingExam);
            return convertToDTO(updatedExam);
        }
        return null; // Retourne null si l'examen n'existe pas
    }

}
