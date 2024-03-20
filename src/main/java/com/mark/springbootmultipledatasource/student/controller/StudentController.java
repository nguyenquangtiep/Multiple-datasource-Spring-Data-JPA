package com.mark.springbootmultipledatasource.student.controller;

import com.mark.springbootmultipledatasource.student.entity.Student;
import com.mark.springbootmultipledatasource.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @PostMapping
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(@RequestBody Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found by id " + id));
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody Student student) {
        Student updateStudent = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found by id " + id));
        updateStudent.setName(student.getName());
        Student savedStudent = studentRepository.save(updateStudent);
        return new ResponseEntity<>(savedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return new ResponseEntity<>("Student has been deleted!", HttpStatus.OK);
    }

}
