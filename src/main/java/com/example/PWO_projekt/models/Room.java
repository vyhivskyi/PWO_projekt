package com.example.PWO_projekt.models;

import jakarta.persistence.*;

@Entity
@Table(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private int roomNumber;
    private int roomDorm;

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL)
    private Student student;

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
        this.student = student;
        student.setRoom(this);
    }

    public void removeStudent() {
        if (this.student != null) {
            this.student.setRoom(null);
            this.student = null;
        }
    }
}
