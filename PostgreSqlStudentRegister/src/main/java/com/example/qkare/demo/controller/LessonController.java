package com.example.qkare.demo.controller;

import com.example.qkare.demo.dto.LessonDto;
import com.example.qkare.demo.dto.StudentDto;
import com.example.qkare.demo.entity.Lesson;
import com.example.qkare.demo.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @PostMapping("/saveLesson")
    public LessonDto addLesson(@RequestBody LessonDto lessonDto) {
        return lessonService.saveLesson(lessonDto);
    }

    @GetMapping("/{lessonCode}")
    public LessonDto getLesson(@PathVariable String lessonCode) {
        return lessonService.findByLessonCode(lessonCode);
    }

    @GetMapping("/listAllLessons")
    public List<LessonDto> getAllLessons() {
        return lessonService.findAll();
    }

    @GetMapping("/{lessonCode}/students")
    public Set<StudentDto> getStudentsByLessonCode(@PathVariable String lessonCode) {
        return lessonService.getStudentsByLessonCode(lessonCode);
    }

    @PostMapping("/assignLecturerToLesson")
    public Lesson assignLecturerToLesson(@RequestParam String lecturerEmail, @RequestParam String lessonCode){
        return lessonService.assignLecturerToLesson(lecturerEmail, lessonCode);
    }
}
