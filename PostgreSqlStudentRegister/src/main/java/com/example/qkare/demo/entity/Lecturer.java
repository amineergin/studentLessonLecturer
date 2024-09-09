package com.example.qkare.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"lecturerEmail"})
public class Lecturer {
    @Id
    @Column(name = "lecturerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lecturer_id;

    @Column(name = "name", nullable = false)
    private String lecturerName;

    @Column(name = "surname", nullable = false)
    private String lecturerSurname;

    @Column(name = "email", unique = true)
    private String lecturerEmail;

    @OneToMany(mappedBy = "lecturer")
    @JsonBackReference
    List<Lesson> lessons;

}