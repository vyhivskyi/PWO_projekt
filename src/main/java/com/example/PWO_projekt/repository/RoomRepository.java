package com.example.PWO_projekt.repository;

import com.example.PWO_projekt.models.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RoomRepository extends CrudRepository<Room, Long> {
    @Query("SELECT r FROM Room r")
    List<Room> findAll();
}

