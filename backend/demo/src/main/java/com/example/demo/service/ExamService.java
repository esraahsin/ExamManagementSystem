package com.example.demo.service;

import com.example.demo.dto.ExamDTO;
import com.example.demo.model.Exam;
import com.example.demo.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    
    public List<ExamDTO> getAllExams() {
        return examRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setExamId(exam.getExamId());
        dto.setSubject(exam.getSubject());
        dto.setExamDate(exam.getExamDate().toLocalDate()); // Convert to LocalDate
        dto.setStartTime(exam.getStartTime().toLocalTime()); // Convert to LocalTime
        dto.setEndTime(exam.getEndTime().toLocalTime()); // Convert to LocalTime
        dto.setDifficulty(exam.getDifficulty());
        dto.setCoefficient(exam.getCoefficient());
        dto.setDepartmentName(exam.getDepartment() != null ? 
            exam.getDepartment().getName() : null);
        dto.setIsDuplicate(exam.isDuplicate());
        return dto;
    }
}