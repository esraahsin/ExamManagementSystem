package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Utilisateur;
import com.example.demo.model.UtilisateurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UtilisateurRepository userRepository;

    
    public Utilisateur addUser(Utilisateur user) {
        return userRepository.save(user);
    }



   
    public Optional<Utilisateur> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    
    public Utilisateur updateUser(Utilisateur user) {
        return userRepository.save(user);
    }

    
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

   
    public List<Utilisateur> getAllUsers() {
        return userRepository.findAll();
    }
}
