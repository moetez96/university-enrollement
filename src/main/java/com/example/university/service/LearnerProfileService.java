package com.example.university.service;

import com.example.university.entity.LearnerProfile;
import com.example.university.repository.LearnerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearnerProfileService {

    private final LearnerProfileRepository learnerProfileRepository;

    public LearnerProfileService(LearnerProfileRepository learnerProfileRepository) {
        this.learnerProfileRepository = learnerProfileRepository;
    }

    public List<LearnerProfile> getActiveStudentProfilesOrderedByGpa(){
        return this.learnerProfileRepository.findAllByIsGraduatedIsFalseOrderByGpaDesc();
    }
}
