package com.example.demo.service;

import com.example.demo.model.Notification;
import com.example.demo.model.NotificationStatus;
import com.example.demo.model.NotificationType;
import com.example.demo.model.User;
import com.example.demo.model.Validation;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // Send a notification to a user
    public Notification sendNotification(int userId, String message, NotificationType type) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setStatus(NotificationStatus.SENT);

        return notificationRepository.save(notification);
    }

   

    // Mark a notification as read
    public Notification markAsRead(int notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setStatus(NotificationStatus.READ);
        return notificationRepository.save(notification);
    }

    // Send a notification when a validation is created or updated
    public Notification sendValidationNotification(Validation validation, String message, NotificationType type) {
        User user = validation.getValidatedBy();
        return sendNotification(user.getUserId(), message, type);
    }
}