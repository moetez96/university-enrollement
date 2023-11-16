package com.example.university.controller;

import com.example.university.entity.Course;
import com.example.university.entity.Program;
import com.example.university.enums.Degree;
import com.example.university.service.ProgramService;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Controller
public class RegistrationController {

    private final StudentService studentService;
    private final ProgramService programService;

    public RegistrationController(StudentService studentService, ProgramService programService) {
        this.studentService = studentService;
        this.programService = programService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("register")
    public String signUpNewStudent(@RequestParam("firstName") String firstName,
                                   @RequestParam("lastName") String lastName,
                                   @RequestParam("email") String email,
                                   @RequestParam("dateOfBirth") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateOfBirth,
                                   @RequestParam("fieldOfStudy") String fieldOfStudy,
                                   @RequestParam("degree") String degreeString) {
        Degree degree = Degree.valueOf(degreeString.toUpperCase(Locale.ROOT));
        Program program = this.programService.getOrCreateProgram(fieldOfStudy, degree);
        this.studentService.addStudent(firstName, lastName, email, dateOfBirth, program);

        return "redirect:/";
    }
}
