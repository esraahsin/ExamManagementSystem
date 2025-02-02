package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ExamDTO;
import com.example.demo.model.Exam;
import com.example.demo.model.Room;
import com.example.demo.repository.ExamRepository;
import com.example.demo.service.ExamService;
import com.example.demo.service.RoomService;
import com.example.demo.service.ExamRoomService;

@RestController
@RequestMapping("/api/admin/exams")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamAdminController {

    @Autowired
    private ExamService examService;

    @Autowired
    private RoomService roomService;

   

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        try {
            List<ExamDTO> exams = examService.getAllExams();
            return ResponseEntity.ok(exams);
        } catch (Exception e) {
            System.err.println("Error in getAllExams: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<ExamDTO> addExam(@RequestBody ExamDTO examDTO) {
        try {
            ExamDTO createdExam = examService.createExam(examDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExam);
        } catch (Exception e) {
            System.err.println("Error in addExam: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Integer id) {
        try {
            boolean isDeleted = examService.deleteExam(id);
            if (isDeleted) {
                return ResponseEntity.noContent().build(); // 204 No Content
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
        } catch (Exception e) {
            System.err.println("Error in deleteExam: " + e.getMessage());
            return ResponseEntity.internalServerError().build(); // 500 Internal Server Error
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Integer id, @RequestBody ExamDTO examDTO) {
        try {
            ExamDTO updatedExam = examService.updateExam(id, examDTO);
            if (updatedExam != null) {
                return ResponseEntity.ok(updatedExam); // 200 OK
            } else {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
        } catch (Exception e) {
            System.err.println("Error in updateExam: " + e.getMessage());
            return ResponseEntity.internalServerError().build(); // 500 Internal Server Error
        }
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> availableRooms = roomService.getAvailableRooms();
        return ResponseEntity.ok(availableRooms); // 🔹 Retourne une réponse HTTP valide
    }

    @GetMapping("/exam-rooms")
    public ResponseEntity<List<Room>> getExamRooms() {
        // Now using the autowired examRoomService
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
}
