package com.mark.springbootmultipledatasource.student.repository;

import com.mark.springbootmultipledatasource.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
