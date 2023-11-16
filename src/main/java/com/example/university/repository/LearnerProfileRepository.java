package com.example.university.repository;

import com.example.university.entity.LearnerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearnerProfileRepository extends JpaRepository<LearnerProfile, Long> {

    List<LearnerProfile> findAllByIsGraduatedIsFalseOrderByGpaDesc();
}
