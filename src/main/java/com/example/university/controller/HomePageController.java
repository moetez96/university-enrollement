package com.example.university.controller;

import com.example.university.entity.Course;
import com.example.university.entity.LearnerProfile;
import com.example.university.entity.Program;
import com.example.university.entity.Student;
import com.example.university.service.CoursesService;
import com.example.university.service.LearnerProfileService;
import com.example.university.service.ProgramService;
import com.example.university.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomePageController {

    private final LearnerProfileService learnerProfileService;
    private final StudentService studentService;
    private final ProgramService programService;
    private final CoursesService coursesService;

    public HomePageController(LearnerProfileService learnerProfileService,
                              StudentService studentService,
                              ProgramService programService,
                              CoursesService coursesService) {

        this.learnerProfileService = learnerProfileService;
        this.studentService = studentService;
        this.programService = programService;
        this.coursesService = coursesService;
    }

    @GetMapping("/courses")
    public String getNumberOfStudentPerCourse(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        List<Course> courses =
                this.coursesService.getAllCourses();
        model.addAttribute("courses", courses);

        model.addAttribute("show_students", false);
        model.addAttribute("show_courses", true);
        model.addAttribute("show_programs", false);
        model.addAttribute("show_scores", false);
        return "public_view";
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        List<Student> allRegisteredStudents = this.studentService.getAllRegisteredStudents();
        model.addAttribute("students", allRegisteredStudents);

        model.addAttribute("show_students", true);
        model.addAttribute("show_courses", false);
        model.addAttribute("show_programs", false);
        model.addAttribute("show_scores", false);
        return "public_view";
    }

    @GetMapping("/scores")
    public String getActiveStudentsSortedByGpa(Model model) {
        List<LearnerProfile> activeStudentsProfiles =
                this.learnerProfileService.getActiveStudentProfilesOrderedByGpa();
        model.addAttribute("students_profiles", activeStudentsProfiles);

        model.addAttribute("show_students", false);
        model.addAttribute("show_courses", false);
        model.addAttribute("show_programs", false);
        model.addAttribute("show_scores", true);
        return "public_view";
    }

    @GetMapping("/programs")
    public String getProgramsWithStudents(Model model) {
        List<Program> programs = this.programService.getAllProgramsWithStudents();
        model.addAttribute("programs", programs);

        model.addAttribute("show_students", false);
        model.addAttribute("show_courses", false);
        model.addAttribute("show_programs", true);
        model.addAttribute("show_scores", false);
        return "public_view";
    }
}
