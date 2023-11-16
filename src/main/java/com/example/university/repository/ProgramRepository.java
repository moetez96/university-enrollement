package com.example.university.repository;

import com.example.university.entity.Program;
import com.example.university.enums.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    Optional<Program> findByFieldOfStudyAndDegree(String fieldOfStudy, Degree degree);
}
