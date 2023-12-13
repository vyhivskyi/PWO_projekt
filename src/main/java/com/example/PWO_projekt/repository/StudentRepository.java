package com.example.PWO_projekt.repository;

import com.example.PWO_projekt.models.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StudentRepository extends CrudRepository<Student, Long> {
    List<Student> findAll();
}
