package com.example.qkare.demo.repository;

import com.example.qkare.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByStudentNumber(String studentNumber);
}
