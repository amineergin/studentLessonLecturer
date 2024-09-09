package com.example.qkare.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"studentNumber"})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @Column(name = "stdName")
    private String name;
    @Column(name = "stdSurname")
    private String surname;
    @Column(name = "stdID", unique = true)
    private String studentNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable( //n-n ilişkiden ortaya çıkan 3.tabloyu yaratır
            name = "student_lesson", //3.tablonun ismi
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "studentId")},
            inverseJoinColumns = {@JoinColumn(name = "lesson_id", referencedColumnName = "lessonId")}
    )
    private Set<Lesson> lessons = new HashSet<>();

}
