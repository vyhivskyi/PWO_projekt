package com.example.PWO_projekt.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private int roomNumber;
    private int roomDorm;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> students = new ArrayList<>();

    public Room(int roomNumber, int roomDorm) {
        this.roomNumber = roomNumber;
        this.roomDorm = roomDorm;
    }

    public Room() {
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomDorm() {
        return roomDorm;
    }
    public void setRoomDorm(Integer roomDorm) {
        this.roomDorm = roomDorm;
    }

    public Long getId() {
        return roomId;
    }
    public void setId(Long roomId) {
        this.roomId = roomId;
    }

    public void assignStudent(Student student) {
        students.add(student);
        student.setRoom(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.setRoom(null);
    }
    public List<Student> getStudents() {
        return students;
    }
}
