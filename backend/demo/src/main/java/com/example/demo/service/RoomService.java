package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.RoomRepository;
import com.example.demo.model.Room;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // Ajouter une salle
    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    // Récupérer une salle par son ID
    public Optional<Room> getRoomById(int roomId) {
        return roomRepository.findById(roomId);
    }


    // Mettre à jour une salle
    public Room updateRoom(Room room) {
        if (roomRepository.existsById(room.getRoomId())) {
            return roomRepository.save(room);
        }
        return null; // ou lancer une exception si nécessaire
    }

    // Supprimer une salle
    public void deleteRoom(int roomId) {
        roomRepository.deleteById(roomId);
    }

    // Récupérer toutes les salles
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    public List<Room> getAvailableRooms() {
        return roomRepository.findByIsAvailableTrue(); // 🔹 Récupération des salles disponibles
    }
}
