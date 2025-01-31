package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.InvigilatorRepository;
import com.example.demo.model.Invigilator;
import java.util.List;


@Service
public class InvigilatorService {

    @Autowired
    private InvigilatorRepository invigilatorRepository;

    // Ajouter un surveillant à un examen et une salle
    public Invigilator assignInvigilator(Invigilator invigilator) {
        return invigilatorRepository.save(invigilator);
    }


    // Mettre à jour l'affectation d'un surveillant
    public Invigilator updateInvigilatorAssignment(Invigilator invigilator) {
        if (invigilatorRepository.existsById(invigilator.getInvigilatorId())) {
            return invigilatorRepository.save(invigilator);
        }
        return null; // ou lancer une exception si nécessaire
    }

    // Supprimer l'affectation d'un surveillant
    public void removeInvigilatorAssignment(int invigilatorId) {
        invigilatorRepository.deleteById(invigilatorId);
    }

    // Récupérer toutes les affectations de surveillants
    public List<Invigilator> getAllInvigilatorAssignments() {
        return invigilatorRepository.findAll();
    }
}

