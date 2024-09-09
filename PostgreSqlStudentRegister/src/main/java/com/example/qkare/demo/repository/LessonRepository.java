package com.example.qkare.demo.repository;

import com.example.qkare.demo.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    Optional<Lesson> findByLessonCode(String lessonCode);
}
