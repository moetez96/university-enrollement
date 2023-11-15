package com.example.university.service;

import com.example.university.entity.LearnerProfile;
import com.example.university.repository.LearnerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnerProfileService {

    @Autowired
    private LearnerProfileRepository learnerProfileRepository;

    //TODO: Returns all the active students' learner profiles in the database,
    //     * sorted by their GPA in descending order
    public List<LearnerProfile> getActiveStudentProfileOrderedByGpa(){
        return List.of();
    }
}
