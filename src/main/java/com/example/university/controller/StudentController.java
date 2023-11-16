package com.example.university.controller;

import com.example.university.entity.Student;
import com.example.university.service.CoursesService;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CoursesService coursesService;

    @RequestMapping("/student")
    public String getStudentProfile(@RequestParam("student_id") long studentId, Model model) {

        Optional<Student> student = this.studentService.findStudentById(studentId);

        if (student.isEmpty()) {
            System.err.printf("Student with Id %d doesn't exist%n", studentId);
            return "redirect:/";
        }

        model.addAttribute("student_id", studentId);
        model.addAttribute(student.get());
        showStudentProfile(model);

        return "student_view";
    }

    private void showStudentProfile(Model model) {
        model.addAttribute("show_courses", false);
    }
    private void showAllCourses(Model model) {
        model.addAttribute("show_courses", true);
    }


}
