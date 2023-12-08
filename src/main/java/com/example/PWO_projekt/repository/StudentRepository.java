package com.example.PWO_projekt.repository;

import com.example.PWO_projekt.models.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface StudentRepository extends CrudRepository<Student, Long> {
    @Query("SELECT s FROM Student s")
    List<Student> customFindAll();
}
