package com.example.university.controller;

import com.example.university.entity.Course;
import com.example.university.entity.Student;
import com.example.university.service.CoursesService;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class StudentController {

    private final StudentService studentService;
    private final CoursesService coursesService;

    public StudentController(StudentService studentService,
                              CoursesService coursesService) {
        this.studentService = studentService;
        this.coursesService = coursesService;
    }

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

    @GetMapping("/student/courses")
    public String getAllCourses(@RequestParam("student_id") long studentId, Model model) {

        Optional<Student> student = this.studentService.findStudentById(studentId);
        if (student.isEmpty()) {
            System.err.printf("Student with Id %d doesn't exist%n", studentId);
            return "redirect:/";
        }

        model.addAttribute("student_id", studentId);
        model.addAttribute("all_courses", this.coursesService.getAllCourses());
        model.addAttribute("student_courses",
                this.studentService.getAllStudentCourses(student.get()));
        showAllCourses(model);

        return "student_view";
    }

    @PostMapping("/student/enroll")
    public String enrollInCourse(@RequestParam("student_id") Long studentId,
                                 @RequestParam("course_id") Long courseId,
                                 Model model) {

        Optional<Student> student = this.studentService.findStudentById(studentId);

        if (student.isEmpty()) {
            System.err.printf("Trying to enroll a student with Id %d that doesn't exist%n",
                    studentId);
            return "redirect:/";
        }

        Optional<Course> course = coursesService.findCourse(courseId);
        if (course.isEmpty()) {
            System.err.printf("Trying to enroll a student to course Id: %d that doesn't exist%n",
                    courseId);
            return "redirect:/";
        }

        this.coursesService.enrollStudent(course.get(), student.get());

        model.addAttribute("student_id", studentId);
        model.addAttribute("all_courses", this.coursesService.getAllCourses());
        model.addAttribute("student_courses",
                this.studentService.getAllStudentCourses(student.get()));
        showAllCourses(model);

        return "student_view";
    }

    @PostMapping("/student/leave_course")
    public String leaveCourse(@RequestParam("student_id") Long studentId,
                              @RequestParam("course_id") Long courseId,
                              Model model) {
        Optional<Student> student = this.studentService.findStudentById(studentId);

        if (student.isEmpty()) {
            System.err.printf("Trying to un-enroll a student with Id %d that doesn't exist%n",
                    studentId);
            return "redirect:/";
        }

        Optional<Course> course = coursesService.findCourse(courseId);
        if (course.isEmpty()) {
            System.err.printf("Trying to un-enroll a student from course Id: %d that doesn't " +
                            "exist%n",
                    courseId);
            return "redirect:/";
        }

        coursesService.unEnrollStudent(course.get(), student.get());

        model.addAttribute("student_id", studentId);
        model.addAttribute("all_courses", coursesService.getAllCourses());
        model.addAttribute("student_courses",
                this.studentService.getAllStudentCourses(student.get()));

        showAllCourses(model);
        return "student_view";
    }

    private void showStudentProfile(Model model) {
        model.addAttribute("show_courses", false);
    }
    private void showAllCourses(Model model) {
        model.addAttribute("show_courses", true);
    }


}
