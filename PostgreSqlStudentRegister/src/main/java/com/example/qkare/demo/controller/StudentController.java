package com.example.qkare.demo.controller;

import com.example.qkare.demo.dto.LessonDto;
import com.example.qkare.demo.dto.StudentDto;
import com.example.qkare.demo.service.LessonService;
import com.example.qkare.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private LessonService lessonService;

    @PostMapping("/registerStudent")
    public StudentDto addStudent(@RequestBody StudentDto studentDto) {
        return studentService.saveStudent(studentDto);
    }

    @GetMapping("/{studentNumber}")
    public StudentDto getStudentByStudentNumber(@PathVariable String studentNumber) {
        return studentService.findByStudentNumber(studentNumber);
    }

    @GetMapping("listAllStudents")
    public List<StudentDto> getAllStudents() {
        return studentService.findAll();
    }

    @PostMapping("/saveStudentToLesson")
    public String enrollStudentToLesson(@RequestParam String studentNumber, @RequestParam String lessonNumber) {
        return studentService.saveStudentToLesson(studentNumber, lessonNumber);
    }

    @GetMapping("/{studentNumber}/lessons")
    public Set<LessonDto> getLessonsByStudentNumber(@PathVariable String studentNumber) {
        return studentService.getLessonsByStudentNumber(studentNumber);
    }

}
