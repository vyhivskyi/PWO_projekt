package com.example.PWO_projekt;

import com.example.PWO_projekt.controller.StudentController;
import com.example.PWO_projekt.models.Student;
import com.example.PWO_projekt.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PwoProjektApplicationTests {

	@Test
	void testGetAllStudents() {
		StudentRepository studentRepository = mock(StudentRepository.class);
		StudentController studentController = new StudentController(studentRepository);

		List<Student> expectedStudents = Arrays.asList(
				new Student("Daria", "Vovk", "daria@gmail.com", 4),
				new Student("Artem", "Vyhivskyi", "artem@gmail.com", 4)
		);

		when(studentRepository.findAll()).thenReturn(expectedStudents);

		List<Student> actualStudents = studentController.getAllStudents();

		assertNotNull(actualStudents);
		assertEquals(expectedStudents.size(), actualStudents.size());
	}

	@Test
	void testGetStudentById() {
		StudentRepository studentRepository = mock(StudentRepository.class);
		StudentController studentController = new StudentController(studentRepository);

		Long studentId = 1L;
		Student expectedStudent = new Student("Daria", "Vovk", "daria@gmail.com", 4);

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

		ResponseEntity<Student> responseEntity = studentController.getStudentById(studentId);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expectedStudent, responseEntity.getBody());
	}

	@Test
	void testAddStudent() {
		StudentRepository studentRepository = mock(StudentRepository.class);
		StudentController studentController = new StudentController(studentRepository);

		Student newStudent = new Student("Artem", "Vyhivskyi", "artem@gmail.com", 4);

		when(studentRepository.save(newStudent)).thenReturn(newStudent);

		ResponseEntity<Student> responseEntity = studentController.addStudent(newStudent);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(newStudent, responseEntity.getBody());
	}

	@Test
	void testUpdateStudent() {
		StudentRepository studentRepository = mock(StudentRepository.class);
		StudentController studentController = new StudentController(studentRepository);

		Long studentId = 1L;
		Student existingStudent = new Student("Daria", "Vovk", "daria@gmail.com", 4);
		Student updatedStudent = new Student("Dariaa", "Vovkk", "daria@example.com", 2);

		when(studentRepository.existsById(studentId)).thenReturn(true);
		when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);

		ResponseEntity<Student> responseEntity = studentController.updateStudent(studentId, updatedStudent);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(updatedStudent, responseEntity.getBody());
	}

}
