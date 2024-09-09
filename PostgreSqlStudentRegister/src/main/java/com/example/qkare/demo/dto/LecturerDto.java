package com.example.qkare.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class LecturerDto {
    private int lecturer_id;
    private String lecturerName;
    private String lecturerSurname;
    private String lecturerEmail;
    private List<LessonDto> lessons;
}
