package com.example.qkare.demo.service;

import com.example.qkare.demo.dto.LessonDto;
import com.example.qkare.demo.dto.StudentDto;
import com.example.qkare.demo.entity.Lesson;
import com.example.qkare.demo.entity.Student;
import com.example.qkare.demo.repository.LessonRepository;
import com.example.qkare.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    public StudentDto saveStudent(StudentDto studentDto) {
        Optional<Student> existingStudent = studentRepository.findByStudentNumber(studentDto.getStudentNumber());
        if (existingStudent.isPresent()) {
            // Konsola mesaj yazdır
            System.out.println("Böyle bir öğrenci numarası zaten mevcut: " + studentDto.getStudentNumber());
            // İsterseniz bir hata fırlatabilirsiniz veya başka bir işlem yapabilirsiniz
            return null; // veya uygun bir işlem yapılabilir
        }

        Student student = new Student();
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setStudentNumber(studentDto.getStudentNumber());
        Student savedStudent = studentRepository.save(student);
        return convertToDto(savedStudent);
    }

    public StudentDto findByStudentNumber(String studentNumber) {
        System.out.println("Searching for student with number: " + studentNumber);
        Optional<Student> student = studentRepository.findByStudentNumber(studentNumber);
        if (student.isPresent()) {
            System.out.println("Student found: " + student.get());
            return convertToDto(student.get());
        } else {
            System.out.println("No student found with number: " + studentNumber);
            return null;
        }
    }

    public List<StudentDto> findAll() {
        return studentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public String saveStudentToLesson(String studentNumber, String lessonCode) {
        Optional<Student> studentOptional = studentRepository.findByStudentNumber(studentNumber);
        Optional<Lesson> lessonOptional = lessonRepository.findByLessonCode(lessonCode);

        if (studentOptional.isEmpty()) {
            return "Öğrenci bulunamadı.";
        }

        if (lessonOptional.isEmpty()) {
            return "Ders bulunamadı.";
        }

        Student student = studentOptional.get();
        Lesson lesson = lessonOptional.get();

        if (student.getLessons().contains(lesson)) {
            return "Öğrenci zaten bu derste kayıtlı.";
        }

        student.getLessons().add(lesson);
        lesson.getStudents().add(student);

        studentRepository.save(student);
        lessonRepository.save(lesson);

        return "Öğrenci başarıyla derse kaydedildi.";
    }

    public Set<LessonDto> getLessonsByStudentNumber(String studentNumber) {
        Optional<Student> studentOptional = studentRepository.findByStudentNumber(studentNumber);
        if (studentOptional.isEmpty()) {
            return Set.of(); // Öğrenci bulunamadığında boş set döndür
        }

        Student student = studentOptional.get();
        return student.getLessons().stream()
                .map(this::convertToLessonDto)
                .collect(Collectors.toSet());
    }

    private StudentDto convertToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getStudentId());
        dto.setName(student.getName());
        dto.setSurname(student.getSurname());
        dto.setStudentNumber(student.getStudentNumber());
        dto.setLessons(student.getLessons().stream()
                .map(lesson -> {
                    LessonDto lessonDto = new LessonDto();
                    lessonDto.setLessonId(lesson.getLessonId());         // lessonId int
                    lessonDto.setLessonName(lesson.getLessonName()); // lessonName String
                    lessonDto.setLessonAkts(lesson.getLessonAkts()); // lessonAkts int
                    lessonDto.setLessonCode(lesson.getLessonCode()); // lessonCode String
                    return lessonDto;
                })
                .collect(Collectors.toSet()));

        return dto;
    }

    private LessonDto convertToLessonDto(Lesson lesson){
        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setLessonName(lesson.getLessonName());
        lessonDto.setLessonAkts(lesson.getLessonAkts());
        lessonDto.setLessonCode(lesson.getLessonCode());

        return lessonDto;
    }
}
