package com.example.qkare.demo.controller;

import com.example.qkare.demo.dto.LecturerDto;
import com.example.qkare.demo.entity.Lecturer;
import com.example.qkare.demo.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturers")
public class LecturerController {
    @Autowired
    private LecturerService lecturerService;

    @PostMapping("/addLecturer")
    public LecturerDto addLecturer(@RequestBody LecturerDto lecturerDto) {
        return lecturerService.saveLecturer(lecturerDto);
    }

    @GetMapping("/listAllLecturer")
    public List<Lecturer> getAllLecturer(){
        return lecturerService.getAllLecturers();
    }

    @GetMapping("/findLecturerByEmail/{lecturerEmail}")
    public LecturerDto getLecturerByEmail(@PathVariable String lecturerEmail){
        return lecturerService.findByLecturerEmail(lecturerEmail);
    }

    @GetMapping("/{lecturerEmail}")
    public LecturerDto getLecturerWithLessons(@PathVariable String lecturerEmail){
        return lecturerService.getLecturerWithLessons(lecturerEmail);
    }
}
