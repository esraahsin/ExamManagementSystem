package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.RoomService;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")  // URL de base pour les requêtes liées aux salles
public class RoomController {

    @Autowired
    private RoomService roomService;  // Injection du service RoomService
    @Autowired
    private RoomRepository roomRepository;

    // Endpoint pour récupérer toutes les salles
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();  // Récupérer toutes les salles
        return ResponseEntity.ok(rooms);  // Retourner la liste des salles avec un code 200 OK
    }

    // Endpoint pour récupérer les salles disponibles
    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> availableRooms = roomService.getAvailableRooms();  // Récupérer les salles disponibles
        return ResponseEntity.ok(availableRooms);  // Retourner les salles disponibles avec un code 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Integer id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) {
            return ResponseEntity.badRequest().body("Salle non trouvée");
        }
        return ResponseEntity.ok(room);
    }
}
