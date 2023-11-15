package com.example.university.service;

import com.example.university.entity.Program;
import com.example.university.enums.Degree;
import com.example.university.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    /*TODO: Returns the Program object by field of study and degree from the database if it exists
            or creates a new object */
    public Program getOrCreateProgram(String fieldOfStudy, Degree degree) {

        Optional<Program> program = Optional.empty(); // placeholder

        if (program.isPresent()) {
            return program.get();
        }

        return new Program(fieldOfStudy, degree);
    }

    public List<Program> getAllProgramsWithStudents() {
        return programRepository.findAll();
    }
}
