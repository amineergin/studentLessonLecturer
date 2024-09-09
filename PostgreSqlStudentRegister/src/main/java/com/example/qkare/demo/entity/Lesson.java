package com.example.qkare.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table
@Getter
@Setter
@EqualsAndHashCode(of = {"lessonCode"})
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lessonId;

    @Column(name="lessonName")
    private String lessonName;

    @Column(name = "AKTS")
    private int lessonAkts;

    @Column(name = "lessonCode", unique = true)
    private String lessonCode;

    @ManyToMany(mappedBy = "lessons", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "lecturerId", referencedColumnName = "lecturerId")
    @JsonManagedReference
    private Lecturer lecturer;
}
