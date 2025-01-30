package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByCapacityGreaterThanEqual(int capacity);
    
    List<Room> findByIsAvailable(boolean isAvailable);
    
    @Query("SELECT r FROM Room r WHERE r.capacity >= :minCapacity AND r.isAvailable = true")
    List<Room> findAvailableRoomsWithCapacity(@Param("minCapacity") int minCapacity);
}