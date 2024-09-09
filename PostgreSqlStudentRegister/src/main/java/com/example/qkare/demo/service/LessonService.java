package com.example.qkare.demo.service;

import com.example.qkare.demo.dto.LecturerDto;
import com.example.qkare.demo.dto.LessonDto;
import com.example.qkare.demo.dto.StudentDto;
import com.example.qkare.demo.entity.Lesson;
import com.example.qkare.demo.entity.Student;
import com.example.qkare.demo.entity.Lecturer;
import com.example.qkare.demo.repository.LecturerRepository;
import com.example.qkare.demo.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    public LessonDto saveLesson(LessonDto lessonDto) {
        Optional<Lesson> existingLesson = lessonRepository.findByLessonCode(lessonDto.getLessonCode());
        if (existingLesson.isPresent()) {
            System.out.println("Böyle bir ders kaydı zaten mevcut: " + lessonDto.getLessonCode());
            return null;
        }

        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonDto.getLessonName());
        lesson.setLessonAkts(lessonDto.getLessonAkts());
        lesson.setLessonCode(lessonDto.getLessonCode());
        Lesson savedLesson = lessonRepository.save(lesson);
        return convertToLessonDto(savedLesson);
    }

    public LessonDto findByLessonCode(String lessonCode) {
        Optional<Lesson> lesson = lessonRepository.findByLessonCode(lessonCode);
        if(lesson.isPresent()) {
            System.out.println("Lesson found: " + lesson.get());
            return convertToLessonDto(lesson.get());
        }
        else{
            System.out.println("There is no lesson: " + lessonCode);
            return null;
        }
    }

    public List<LessonDto> findAll(){
        return lessonRepository.findAll().stream()
                .map(this::convertToLessonDto)
                .collect(Collectors.toList());
    }

    public Set<StudentDto> getStudentsByLessonCode(String lessonCode) {
        Optional<Lesson> lessonOptional = lessonRepository.findByLessonCode(lessonCode);
        if (lessonOptional.isEmpty()) {
            return Set.of(); // Ders bulunamadığında boş set döndür
        }

        Lesson lesson = lessonOptional.get();
        return lesson.getStudents().stream()
                .map(this::convertToStudentDto)
                .collect(Collectors.toSet());
    }

    public Lesson assignLecturerToLesson(String lecturerEmail, String lessonCode){
        Lecturer lecturer = lecturerRepository.findByLecturerEmail(lecturerEmail)
                .orElseThrow(() -> new RuntimeException("Lecturer not found!"));

        Lesson lesson = lessonRepository.findByLessonCode(lessonCode)
                .orElseThrow(() -> new RuntimeException("Lesson not found!"));

        lesson.setLecturer(lecturer);
        return lessonRepository.save(lesson);
    }

    private LessonDto convertToLessonDto(Lesson lesson){
        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setLessonName(lesson.getLessonName());
        lessonDto.setLessonAkts(lesson.getLessonAkts());
        lessonDto.setLessonCode(lesson.getLessonCode());

        Lecturer lecturer = lesson.getLecturer();
        if (lecturer != null) {
            LecturerDto lecturerDto = new LecturerDto();
            lecturerDto.setLecturer_id(lecturer.getLecturer_id());
            lecturerDto.setLecturerName(lecturer.getLecturerName());
            lecturerDto.setLecturerEmail(lecturer.getLecturerEmail());
            lessonDto.setLecturer(lecturerDto);
        }
        return lessonDto;
    }

    private StudentDto convertToStudentDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getStudentId());
        dto.setName(student.getName());
        dto.setSurname(student.getSurname());
        dto.setStudentNumber(student.getStudentNumber());
        return dto;
    }
}
