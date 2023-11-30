package com.example.university.controller;

import com.example.university.entity.Course;
import com.example.university.entity.Student;
import com.example.university.forms.PasswordForm;
import com.example.university.forms.UpdateForm;
import com.example.university.security.CustomUserDetails;
import com.example.university.service.CoursesService;
import com.example.university.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@PreAuthorize("isAuthenticated()")
public class StudentController {

    private final StudentService studentService;
    private final CoursesService coursesService;

    @Autowired
    public StudentController(StudentService studentService,
                              CoursesService coursesService) {
        this.studentService = studentService;
        this.coursesService = coursesService;
    }

    @RequestMapping("/student")
    public String getStudentProfile(@RequestParam("student_id") long studentId,
                                    Model model, Authentication authentication) {

        Optional<Student> student = this.studentService.findStudentById(studentId);

        if (student.isEmpty()) {
            System.err.printf("Student with Id %d doesn't exist%n", studentId);
            return "redirect:/";
        }

        if (isAuthenticatedStudent(authentication, studentId)) {
            return "access-denied";
        }

        model.addAttribute("student_id", studentId);
        model.addAttribute(student.get());
        showStudentProfile(model);

        return "student_view";
    }

    @GetMapping("/student/courses")
    public String getAllCourses(@RequestParam("student_id") long studentId,
                                Model model, Authentication authentication) {

        Optional<Student> student = this.studentService.findStudentById(studentId);

        if (student.isEmpty()) {
            System.err.printf("Student with Id %d doesn't exist%n", studentId);
            return "redirect:/";
        }

        if (isAuthenticatedStudent(authentication, studentId)) {
            return "access-denied";
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
                                 Model model, Authentication authentication) {

        Optional<Student> student = this.studentService.findStudentById(studentId);

        if (student.isEmpty()) {
            System.err.printf("Trying to enroll a student with Id %d that doesn't exist%n",
                    studentId);
            return "redirect:/";
        }

        if (isAuthenticatedStudent(authentication, studentId)) {
            return "access-denied";
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
                              Model model, Authentication authentication) {
        Optional<Student> student = this.studentService.findStudentById(studentId);

        if (student.isEmpty()) {
            System.err.printf("Trying to un-enroll a student with Id %d that doesn't exist%n",
                    studentId);
            return "redirect:/";
        }

        if (isAuthenticatedStudent(authentication, studentId)) {
            return "access-denied";
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

    @GetMapping("/student/update/{student_id}")
    public String updateStudentInformation(@PathVariable("student_id") long studentId,
                                           Model model,
                                           Authentication authentication) {

        UpdateForm updateForm = new UpdateForm();

        Optional<Student> student = studentService.findStudentById(studentId);

        if (isAuthenticatedStudent(authentication, studentId) || student.isEmpty()) {
            return "access-denied";
        }

        updateForm.setEmail(student.get().getEmail());
        updateForm.setFirstName(student.get().getFirstName());
        updateForm.setLastName(student.get().getLastName());

        model.addAttribute("updateForm", updateForm);
        model.addAttribute("student", student.get());

        return "update_student";
    }


    @PostMapping("/student/update/{student_id}")
    public String processUpdateStudentInformation(@PathVariable("student_id") long studentId,
                                                  @Valid @ModelAttribute("updateForm") UpdateForm updateForm,
                                                  BindingResult result,
                                                  Model model,
                                                  Authentication authentication) {

        Optional<Student> student = studentService.findStudentById(studentId);

        if (isAuthenticatedStudent(authentication, studentId) || student.isEmpty()) {
            return "access-denied";
        }

        model.addAttribute("student", student.get());

        if (result.hasErrors()) {
            model.addAttribute("updateForm", updateForm);
            return "update_student";
        }

        if (this.studentService.emailExistsForOtherStudents(updateForm.getEmail(), studentId)) {
            result.rejectValue("email", "DuplicateKey.studentForm.email", "Email address is already in use.");
            return "update_student";
        }

        studentService.updateStudentInformation(student.get(),
                updateForm.getFirstName(),
                updateForm.getLastName(),
                updateForm.getEmail());

        return "redirect:/student?student_id=" + studentId;
    }

    @GetMapping("/student/change_password/{student_id}")
    public String changePassword(@PathVariable("student_id") long studentId,
                                           Model model,
                                           Authentication authentication) {

        PasswordForm passwordForm = new PasswordForm();

        Optional<Student> student = studentService.findStudentById(studentId);

        if (isAuthenticatedStudent(authentication, studentId) || student.isEmpty()) {
            return "access-denied";
        }

        model.addAttribute("passwordForm", passwordForm);
        model.addAttribute("student", student.get());

        return "change_password";
    }

    @PostMapping("/student/change_password/{student_id}")
    public String processChangePassword(@PathVariable("student_id") long studentId,
                                                  @Valid @ModelAttribute("passwordForm") PasswordForm passwordForm,
                                                  BindingResult result,
                                                  Model model,
                                                  Authentication authentication) {

        Optional<Student> student = studentService.findStudentById(studentId);

        if (isAuthenticatedStudent(authentication, studentId) || student.isEmpty()) {
            return "access-denied";
        }

        model.addAttribute("student", student.get());

        if (result.hasErrors()) {
            model.addAttribute("passwordForm", passwordForm);
            return "change_password";
        }

        if (!this.studentService.comparePassword(passwordForm.getOldPassword(), student.get().getPassword())) {
            result.rejectValue("oldPassword", "Mismatch.passwordForm.oldPassword", "Password not correct");
            return "change_password";
        }

        if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "Mismatch.passwordForm.confirmPassword", "Password mismatch");
            return "change_password";
        }

        studentService.changePassword(student.get(),
                passwordForm.getNewPassword());

        return "redirect:/student?student_id=" + studentId;
    }

    private boolean isAuthenticatedStudent(Authentication authentication, long studentId) {

        long authenticatedUserId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        return authenticatedUserId != studentId;
    }

    private void showStudentProfile(Model model) {
        model.addAttribute("show_courses", false);
    }

    private void showAllCourses(Model model) {
        model.addAttribute("show_courses", true);
    }

}
