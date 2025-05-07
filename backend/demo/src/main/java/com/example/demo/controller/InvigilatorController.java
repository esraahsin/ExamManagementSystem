package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.InvigilatorService;
import com.example.demo.model.Invigilator;
import com.example.demo.repository.InvigilatorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/invigilators")
public class InvigilatorController {

    @Autowired
    private InvigilatorService invigilatorService;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Autowired
    private InvigilatorRepository invigilatorRepository;

    @GetMapping("/available")
    @ResponseBody
    public ResponseEntity<?> getAvailableInvigilators(
            @RequestParam int examId,
            @RequestParam String examDate,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        try {
            List<User> availableInvigilators = invigilatorService.getAvailableInvigilators(examId, examDate, startTime, endTime);
            return ResponseEntity.ok(availableInvigilators);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Paramètres invalides : " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Examen non trouvé : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @GetMapping
public ResponseEntity<?> getInvigilations(@RequestParam Integer user_id) {
    // Trouver l'utilisateur par son ID
    User teacher = userRepository.findByUserId(user_id);
    if (teacher == null) {
        return ResponseEntity.badRequest().body("Utilisateur non trouvé avec l'ID : " + user_id);
    }

    // Récupérer les surveillances de l'utilisateur
    List<Invigilator> invigilations = invigilatorRepository.findByUser(teacher);

    // Transformer les données pour inclure exam_id et room_id
    List<Map<String, Object>> response = invigilations.stream()
        .map(invigilation -> {
            Map<String, Object> invigilationData = new HashMap<>();
            invigilationData.put("invigilatorId", invigilation.getInvigilatorId());
            invigilationData.put("createdAt", invigilation.getCreatedAt());
            invigilationData.put("exam_id", invigilation.getExam().getExamId()); // Assurez-vous que Exam a un getExamId()
            invigilationData.put("room_id", invigilation.getRoom().getRoomId()); // Assurez-vous que Room a un getRoomId()
            return invigilationData;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(response);
}
}
