package com.example.qkare.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class StudentDto {
    private int id;
    private String name;
    private String surname;
    private String studentNumber;

    private Set<LessonDto> lessons;
    //Eğer StudentDto'yu döndürürken öğrenci ile ilgili ders bilgilerini de göstermek istiyorsak bir üst satırdaki kodlar yazılmalıdır.
}
