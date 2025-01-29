package com.example.demo.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import jakarta.persistence.*;
@Entity
@Table(name = "exams")
public class Exam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int examId;
    
    @Column(nullable = false, length = 100)
    private String subject;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
        @JsonIgnoreProperties({"exams", "hibernateLazyInitializer", "handler"})

    private Department department;
    
    @Column(nullable = false)
    private Date examDate;
    
    @Column(nullable = false)
    private Time startTime;
    
    @Column(nullable = false)
    private Time endTime;
    
    private Integer difficulty;
    
    private Integer coefficient;
    
    @Column(nullable = false)
    private Date createdAt;
    
    @Column(nullable = false)
    private Date updatedAt;
    
    private boolean isDuplicate;
    
    @OneToMany(mappedBy = "exam")
    private List<ExamRoom> examRooms;
    
    @OneToMany(mappedBy = "exam")
    private List<Invigilator> invigilators;
    
    @OneToMany(mappedBy = "exam")
    private List<ExamStudent> examStudents;

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public List<ExamRoom> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(List<ExamRoom> examRooms) {
        this.examRooms = examRooms;
    }

    public List<Invigilator> getInvigilators() {
        return invigilators;
    }

    public void setInvigilators(List<Invigilator> invigilators) {
        this.invigilators = invigilators;
    }

    public List<ExamStudent> getExamStudents() {
        return examStudents;
    }

    public void setExamStudents(List<ExamStudent> examStudents) {
        this.examStudents = examStudents;
    }
}
