package com.example.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

// ExamStudent Entity
@Entity
@Table(name = "exam_students")
public class ExamStudent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int examStudentId;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    @JsonBackReference
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @JsonBackReference
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamStudentStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    // Constructors
    public ExamStudent() {}

    public ExamStudent(Exam exam, Student student, ExamStudentStatus status) {
        this.exam = exam;
        this.student = student;
        this.status = status;
    }

    // Getters and Setters
    public int getExamStudentId() {
        return examStudentId;
    }

    public void setExamStudentId(int examStudentId) {
        this.examStudentId = examStudentId;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ExamStudentStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStudentStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}