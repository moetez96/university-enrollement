package com.example.university.entity;

import com.example.university.enums.Degree;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final Map<Degree, GraduationRequirements> REQUIREMENTS_MAP = new HashMap<>();

    static {
        REQUIREMENTS_MAP.put(Degree.ASSOCIATE, new GraduationRequirements(3, 60));
        REQUIREMENTS_MAP.put(Degree.BACHELOR, new GraduationRequirements(4, 160));
        REQUIREMENTS_MAP.put(Degree.MASTER, new GraduationRequirements(2, 90));
        REQUIREMENTS_MAP.put(Degree.PHD, new GraduationRequirements(5, 200));
    }


    public Program() {
    }

    public Program(String fieldOfStudy, Degree degree) {
        this.fieldOfStudy = fieldOfStudy;
        this.degree = degree;
        this.setRequirements(degree);
    }

    public Long getProgramId() {
        return this.programId;
    }

    public int getRequiredYearsToGraduation() {
        return this.requiredYearsToGraduation;
    }

    public int getRequiredCreditsToGraduation() {
        return this.requiredCreditsToGraduation;
    }

    public String getFieldOfStudy() {
        return this.fieldOfStudy;
    }

    public Degree getDegree() {
        return this.degree;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void setRequirements(Degree degree) {
        GraduationRequirements requirements = REQUIREMENTS_MAP
                .getOrDefault(degree, new GraduationRequirements(0, 0));

        this.requiredYearsToGraduation = requirements.getYears();
        this.requiredCreditsToGraduation = requirements.getCredits();
    }

    private static class GraduationRequirements {
        private final int years;
        private final int credits;

        public GraduationRequirements(int years, int credits) {
            this.years = years;
            this.credits = credits;
        }

        public int getYears() {
            return years;
        }

        public int getCredits() {
            return credits;
        }
    }
}
