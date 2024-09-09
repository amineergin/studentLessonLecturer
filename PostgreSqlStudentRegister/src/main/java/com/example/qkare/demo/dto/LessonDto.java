package com.example.qkare.demo.dto;

import lombok.Data;

@Data
public class LessonDto {
    private int lessonId;
    private String lessonName;
    private int lessonAkts;
    private String lessonCode;

    private LecturerDto lecturer;
}
