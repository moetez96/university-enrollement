package com.example.university.controller;

import com.example.university.entity.Student;
import com.example.university.service.AuthService;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final StudentService studentService;
    private final AuthService authService;

    @Autowired
    public LoginController(StudentService studentService, AuthService authService) {
        this.studentService = studentService;
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @PostMapping("/login")
    public String studentLogin(@RequestParam("email") String email, @RequestParam("password") String password,
                               Model model) {

        Optional<Student> student = this.studentService.findStudentByEmail(email);
        if (student.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("error_message",
                    "Student with this email wasn't found");
            return "login";
        }

        if (!studentService.comparePassword(password, student.get().getPassword())) {
            model.addAttribute("error", true);
            model.addAttribute("error_message", "Password doesn't match");
            return "login";
        }

        this.authService.authenticate(email, password);

        return "redirect:student?student_id=" + student.get().getId();
    }

}