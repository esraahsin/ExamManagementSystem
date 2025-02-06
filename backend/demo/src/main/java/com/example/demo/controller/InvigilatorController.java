package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.InvigilatorService;

import java.util.List;

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
}