package com.example.university.controller;

import com.example.university.entity.Student;
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

    @Autowired
    private StudentService studentService;

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

        if (!student.get().getLastName().equalsIgnoreCase(password)) {
            model.addAttribute("error", true);
            model.addAttribute("error_message", "Password doesn't match");
            return "login";
        }

        return "redirect:/";
    }
}
