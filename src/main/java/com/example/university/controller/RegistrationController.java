package com.example.university.controller;

import com.example.university.entity.Program;
import com.example.university.enums.Degree;
import com.example.university.forms.RegisterForm;
import com.example.university.service.ProgramService;
import com.example.university.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
                                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("registerForm", registerForm);
            return "registration";
        }

        if (registerForm.getDateOfBirth() == null || registerForm.getDateOfBirth().isEmpty()) {
            result.rejectValue("dateOfBirth", "NotEmpty", "Date of birth is required.");
            return "registration";
        }

        LocalDate dateOfBirth;
        try {
            dateOfBirth = LocalDate.parse(registerForm.getDateOfBirth());
        } catch (DateTimeParseException e) {
            result.rejectValue("dateOfBirth", "InvalidFormat", "Invalid date format. Please use yyyy-MM-dd.");
            return "registration";
        }

        Degree degree = Degree.valueOf(registerForm.getDegree().toUpperCase(Locale.ROOT));
        Program program = this.programService.getOrCreateProgram(registerForm.getFieldOfStudy(), degree);
        this.studentService.addStudent(registerForm.getFirstName(), registerForm.getLastName(), registerForm.getEmail(),
                registerForm.getPassword(), dateOfBirth, program);

        return "redirect:/";
    }
}
