package com.example.university.Entity;

import com.example.university.Enums.Degree;

import javax.persistence.*;

@Entity
@Table(name = "Programs")
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;
    private int requiredYearsToGraduation;
    private int requiredCreditsToGraduation;
    private String fieldOfStudy;
    private Degree degree;

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
