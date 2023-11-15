package com.example.university.controller;

import com.example.university.entity.Program;
import com.example.university.enums.Degree;
import com.example.university.service.ProgramService;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Locale;

@Controller
public class RegistrationController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProgramService programService;

    //TODO: Complete the SignUp method
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
