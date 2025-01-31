package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    public User addUser(User user) {
        return userRepository.save(user);
    }



   
    public Optional<User> getUserById(int userId) {
        return userRepository.findById(userId);
    }

    
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

   
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
