package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.*;;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    // Basic CRUD methods inherited from JpaRepository
    
    // Find by user ID
    List<Notification> findByUserId(int userId);
    
    // Find by user ID and status
    List<Notification> findByUserIdAndStatus(int userId, NotificationStatus status);
    
    // Find unread notifications
    @Query("SELECT n FROM Notification n " +
           "WHERE n.user.id = :userId " +
           "AND n.status = 'SENT' " +
           "ORDER BY n.createdAt DESC")
    List<Notification> findUnreadNotifications(@Param("userId") int userId);
    
    // Find recent notifications
    @Query("SELECT n FROM Notification n " +
           "WHERE n.user.id = :userId " +
           "AND n.createdAt >= :date " +
           "ORDER BY n.createdAt DESC")
    List<Notification> findRecentNotifications(
        @Param("userId") int userId,
        @Param("date") Date date
    );
    
    // Find notifications by type and status
    @Query("SELECT n FROM Notification n " +
           "WHERE n.user.id = :userId " +
           "AND n.type = :type " +
           "AND n.status = :status")
    List<Notification> findByUserAndTypeAndStatus(
        @Param("userId") int userId,
        @Param("type") String type,
        @Param("status") NotificationStatus status
    );
}
