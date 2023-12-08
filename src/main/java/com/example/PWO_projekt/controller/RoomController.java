package com.example.PWO_projekt.controller;

import com.example.PWO_projekt.models.Room;
import com.example.PWO_projekt.models.Student;
import com.example.PWO_projekt.repository.RoomRepository;
import com.example.PWO_projekt.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public RoomController(RoomRepository roomRepository, StudentRepository studentRepository) {
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        Room savedRoom = roomRepository.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        if (!roomRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        room.setId(id);
        Room updatedRoom = roomRepository.save(room);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        if (!roomRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        roomRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomId}/assign-student/{studentId}")
    public ResponseEntity<Room> assignStudentToRoom(@PathVariable Long roomId, @PathVariable Long studentId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        Optional<Student> studentOptional = studentRepository.findById(studentId);

        if (roomOptional.isPresent() && studentOptional.isPresent()) {
            Room room = roomOptional.get();
            Student student = studentOptional.get();

            room.assignStudent(student);
            roomRepository.save(room);

            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{roomId}/remove-student")
    public ResponseEntity<Room> removeStudentFromRoom(@PathVariable Long roomId) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);

        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();

            room.removeStudent();
            roomRepository.save(room);

            return ResponseEntity.ok(room);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

