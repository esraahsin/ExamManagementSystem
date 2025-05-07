package com.example.demo.controller;

import com.example.demo.service.ExamRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExamRoomController {

    @Autowired
    private ExamRoomService examRoomService;

    // Méthode pour affecter une salle à un examen
    @PostMapping("/exam_rooms/{examId}/{roomId}")
    public void assignRoomToExam(@PathVariable int examId, @PathVariable int roomId) {
        examRoomService.assignRoomToExam(examId, roomId);
    }
}
