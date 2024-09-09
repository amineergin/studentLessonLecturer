package com.example.qkare.demo.repository;

import com.example.qkare.demo.entity.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
    Optional<Lecturer> findByLecturerEmail(String lecturerEmail);
}
