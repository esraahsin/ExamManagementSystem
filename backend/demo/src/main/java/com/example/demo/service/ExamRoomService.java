package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.ExamRoomRepository;
import com.example.demo.model.ExamRoom;
import java.util.List;
import java.util.Optional;

@Service
public class ExamRoomService {

    @Autowired
    private ExamRoomRepository examRoomRepository;

    // Ajouter une réservation de salle pour un examen
    public ExamRoom addExamRoomReservation(ExamRoom examRoom) {
        return examRoomRepository.save(examRoom);
    }

    

    

    // Mettre à jour une réservation de salle pour un examen
    public ExamRoom updateExamRoomReservation(ExamRoom examRoom) {
        if (examRoomRepository.existsById(examRoom.getExamRoomId())) {
            return examRoomRepository.save(examRoom);
        }
        return null; // ou lancer une exception si nécessaire
    }

    // Supprimer une réservation de salle
    public void deleteExamRoomReservation(int examRoomId) {
        examRoomRepository.deleteById(examRoomId);
    }

    // Récupérer toutes les réservations
    public List<ExamRoom> getAllExamRoomReservations() {
        return examRoomRepository.findAll();
    }
}


