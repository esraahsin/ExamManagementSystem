package com.example.demo.model;

import jakarta.persistence.*;
import com.example.demo.model.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "teaching_assignments")
public class TeachingAssignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User teacher; // L'enseignant assigné

    @Column(name = "subject", nullable = false, length = 100)
    private String subject; // La matière enseignée

    // Constructeurs
    public TeachingAssignment() {}

    public TeachingAssignment(User teacher, String subject) {
        this.teacher = teacher;
        this.subject = subject;
    }

    // Getters et Setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Méthode toString() (utile pour le debug)
    @Override
    public String toString() {
        return "TeachingAssignment{" +
                "assignmentId=" + assignmentId +
                ", teacher=" + (teacher != null ? teacher.getName() : "null") +
                ", subject='" + subject + '\'' +
                '}';
    }
}
