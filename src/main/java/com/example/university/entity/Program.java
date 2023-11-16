package com.example.university.entity;

import com.example.university.enums.Degree;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    @Column(nullable = false)
    private int requiredYearsToGraduation;
    @Column(nullable = false)
    private int requiredCreditsToGraduation;
    @Column(nullable = false, updatable = false)
    private String fieldOfStudy;
    @Enumerated(EnumType.STRING)
    private Degree degree;
    @OneToMany(mappedBy = "program")
    private final List<Student> students = new ArrayList<>();

    public Program() {
    }

    public Program(String fieldOfStudy, Degree degree) {
        this.fieldOfStudy = fieldOfStudy;
        this.degree = degree;
    }

    public Long getProgramId() {
        return programId;
    }

    public int getRequiredYearsToGraduation() {
        return requiredYearsToGraduation;
    }

    public int getRequiredCreditsToGraduation() {
        return requiredCreditsToGraduation;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public Degree getDegree() {
        return degree;
    }
}
