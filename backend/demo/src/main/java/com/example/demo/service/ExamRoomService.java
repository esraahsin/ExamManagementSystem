package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.ExamRoomRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.model.Exam;
import com.example.demo.model.ExamRoom;
import com.example.demo.model.Room;

import java.util.List;
import java.util.Optional;

@Service
public class ExamRoomService {

    @Autowired
    private ExamRoomRepository examRoomRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private RoomRepository roomRepository;

    // Méthode pour affecter une salle à un examen
    public void assignRoomToExam(int examId, int roomId) {
        // Vérifier si l'examen existe
        Optional<Exam> examOptional = examRepository.findById(examId);
        if (!examOptional.isPresent()) {
            throw new IllegalArgumentException("Examen non trouvé pour l'ID : " + examId);
        }

        // Vérifier si la salle existe et est disponible
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (!roomOptional.isPresent()) {
            throw new IllegalArgumentException("Salle non trouvée pour l'ID : " + roomId);
        }

        Room room = roomOptional.get();

        // Vérifier si la salle est disponible
        if (!room.isAvailable()) {
            throw new IllegalArgumentException("La salle " + room.getRoomName() + " n'est pas disponible.");
        }

        // Créer une nouvelle entrée dans la table exam_rooms pour lier l'examen à la salle
        ExamRoom examRoom = new ExamRoom();
        examRoom.setExam(examOptional.get());  // Lier l'objet `Exam` complet
        examRoom.setRoom(room);  // Lier l'objet `Room` complet
        examRoom.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));  // Utilisation de la date actuelle

        // Sauvegarder l'association dans la table exam_rooms
        examRoomRepository.save(examRoom);

        // Mettre à jour la disponibilité de la salle (si la salle est désormais réservée)
        room.setAvailable(false);  // Marquer la salle comme non disponible
        roomRepository.save(room);

        // Optionnel : Mettre à jour l'examen, si nécessaire
        Exam exam = examOptional.get();
        examRepository.save(exam);
    }

    // Ajouter une nouvelle réservation de salle d'examen
    public ExamRoom addExamRoom(ExamRoom examRoom) {
        return examRoomRepository.save(examRoom);
    }

    // Mettre à jour une réservation de salle d'examen existante
    public Optional<ExamRoom> updateExamRoom(int examRoomId, ExamRoom updatedRoom) {
        Optional<ExamRoom> existingRoom = examRoomRepository.findById(examRoomId);
        if (existingRoom.isPresent()) {
            updatedRoom.setExamRoomId(examRoomId);  // Assurez-vous que l'ID est défini correctement
            return Optional.of(examRoomRepository.save(updatedRoom));
        }
        return Optional.empty();  // Retourner un Optional vide si non trouvé
    }

    // Supprimer une réservation de salle d'examen par ID
    public void deleteExamRoom(int examRoomId) {
        examRoomRepository.deleteById(examRoomId);
    }

    // Obtenir toutes les réservations de salles d'examen
    public List<ExamRoom> getAllExamRooms() {
        return examRoomRepository.findAll();
    }

    // Obtenir une réservation de salle d'examen spécifique par ID (optionnel)
    public Optional<ExamRoom> getExamRoomById(int examRoomId) {
        return examRoomRepository.findById(examRoomId);
    }
}
