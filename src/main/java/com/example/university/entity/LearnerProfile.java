package com.example.university.entity;

import javax.persistence.*;

@Entity
@Table(name = "LearnerProfiles")
public class LearnerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    private int numberOfCredits;
    private float gpa;

    @Column(name = "is_graduated")
    private boolean isGraduated;
    private short startYear;
    @OneToOne(mappedBy = "learnerProfile")
    private Student student;

    public LearnerProfile() {
    }

    public LearnerProfile(short startYear) {
        this.startYear = startYear;
    }

    public Long getProfileId() {
        return profileId;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public float getGpa() {
        return gpa;
    }

    public boolean isIsGraduated() {
        return isGraduated;
    }

    public short getStartYear() {
        return startYear;
    }

    public Student getStudent() {
        return student;
    }
}
