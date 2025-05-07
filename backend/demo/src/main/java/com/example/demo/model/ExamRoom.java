package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam_rooms")
public class ExamRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int examRoomId;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    @JsonBackReference
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;

    @Column(nullable = false)
    private Date createdAt;

    public int getExamRoomId() {
        return examRoomId;
    }

    public void setExamRoomId(int examRoomId) {
        this.examRoomId = examRoomId;
    }

    public Exam getExam() {
        return exam;
    }

    // Correction: Mettre l'objet complet 'Exam' au lieu de l'ID
    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
