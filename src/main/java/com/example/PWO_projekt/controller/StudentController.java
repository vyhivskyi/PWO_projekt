package com.example.PWO_projekt.controller;

import com.example.PWO_projekt.models.Student;
import com.example.PWO_projekt.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent != null) {
            // Preserve the existing room information if not provided in the request
            if (student.getRoom() == null) {
                student.setRoom(existingStudent.getRoom());
            }

            student.setId(id);
            Student updatedStudent = studentRepository.save(student);

            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

