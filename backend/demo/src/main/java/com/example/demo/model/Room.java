package com.example.demo.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")

public class Room implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    @Column(nullable = false, length = 50)
    private String roomName;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(length = 100)
    private String location;
    
    private boolean isAvailable;
    
    @Column(nullable = false)
    private Date createdAt;
    
    @Column(nullable = false)
    private Date updatedAt;
    
    @OneToMany(mappedBy = "room")
    @JsonManagedReference
    private List<ExamRoom> examRooms;
    //

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
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

    public List<ExamRoom> getExamRooms() {
        return examRooms;
    }

    public void setExamRooms(List<ExamRoom> examRooms) {
        this.examRooms = examRooms;
    }
}
