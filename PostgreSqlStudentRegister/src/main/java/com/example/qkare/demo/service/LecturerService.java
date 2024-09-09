package com.example.qkare.demo.service;

import com.example.qkare.demo.dto.LecturerDto;
import com.example.qkare.demo.entity.Lecturer;
import com.example.qkare.demo.entity.Lesson;
import com.example.qkare.demo.repository.LecturerRepository;
import com.example.qkare.demo.dto.LessonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LecturerService {

    @Autowired
    private LecturerRepository lecturerRepository;

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    public LecturerDto saveLecturer(LecturerDto lecturerDto) {
        Optional<Lecturer> existingLecturer = lecturerRepository.findByLecturerEmail(lecturerDto.getLecturerEmail());
        if (existingLecturer.isPresent()) {
            // Konsola mesaj yazdır
            System.out.println("Böyle bir email zaten mevcut: " + lecturerDto.getLecturerEmail());
            // İsterseniz bir hata fırlatabilirsiniz veya başka bir işlem yapabilirsiniz
            return null; // veya uygun bir işlem yapılabilir
        }else {
            //lecturerDto'dan alınan verileri lecturer entitysine aktarıyor.DTOlar doğrudan veritabanı ile bağlantılı değildir.
            //Controller ile service katmanı arasına veri taşıma işlemi yapar.
            //Burada DTO'daki lecturerName, lecturerSurname, lecturerEmail alanları entity'deki alanlara aktarılıyor.
            //Bu da şu işe yarıyor; arayüzümüzde kullanıcıya entity'de bulunan her alanı açmıyoruz. Yalnızca bazılarını kullanıcı
            //kullanımına açmak için DTO kullanılıyor.
            Lecturer lecturer = new Lecturer();
            lecturer.setLecturerName(lecturerDto.getLecturerName());
            lecturer.setLecturerSurname(lecturerDto.getLecturerSurname());
            lecturer.setLecturerEmail(lecturerDto.getLecturerEmail());  // Burada düzeltilmiş
            Lecturer savedLecturer = lecturerRepository.save(lecturer);
            return convertToLecturerDto(savedLecturer);
        }
    }

    public LecturerDto findByLecturerEmail(String lecturerEmail){
        Optional<Lecturer> lecturer = lecturerRepository.findByLecturerEmail(lecturerEmail);

        if(!lecturer.isPresent()) {
            System.out.println("No lecturer found with this email: " + lecturer);
            return null;
        }
        return convertToLecturerDto(lecturer.get());
    }

    public LecturerDto getLecturerWithLessons(String lecturerEmail) {
        Lecturer lecturer = lecturerRepository.findByLecturerEmail(lecturerEmail)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        return convertToLecturerDto(lecturer);
    }

    private LecturerDto convertToLecturerDto(Lecturer lecturer) {
        LecturerDto lecturerDto = new LecturerDto();
        lecturerDto.setLecturer_id(lecturer.getLecturer_id());
        lecturerDto.setLecturerName(lecturer.getLecturerName());
        lecturerDto.setLecturerSurname(lecturer.getLecturerSurname());
        lecturerDto.setLecturerEmail(lecturer.getLecturerEmail());
        // Dersleri DTO'ya dönüştürüyoruz
        List<LessonDto> lessonDtos = lecturer.getLessons().stream().map(this::convertToLessonDto).toList();
        lecturerDto.setLessons(lessonDtos);
        return lecturerDto;
    }

    private LessonDto convertToLessonDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setLessonId(lesson.getLessonId());
        lessonDto.setLessonName(lesson.getLessonName());
        lessonDto.setLessonCode(lesson.getLessonCode());
        return lessonDto;
    }
}
