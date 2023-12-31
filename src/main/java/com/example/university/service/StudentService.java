package com.example.university.service;

import com.example.university.entity.Course;
import com.example.university.entity.Program;
import com.example.university.entity.Student;
import com.example.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Student> findStudentByEmail(String email) {
        return this.studentRepository.findByEmail(email);
    }

    public List<Student> getAllRegisteredStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(String firstName, String lastName, String email, String password, LocalDate dateOfBirth, Program program) {
        String encodedPassword = passwordEncoder.encode(password);
        Student student = new Student(firstName, lastName, email, encodedPassword, dateOfBirth, program);
        studentRepository.save(student);
    }

    public Optional<Student> findStudentById(long id) {
        return studentRepository.findById(id);
    }

    public Set<Course> getAllStudentCourses(Student student) {
        return student.getEnrolledIn();
    }

    public boolean emailExistsForOtherStudents(String email, long studentId) {

        Optional<Student> foundStudent = studentRepository.findByEmail(email);

        if (foundStudent.isEmpty()) {
            return false;
        }

        return foundStudent.get().getId() != studentId;
    }

    public void updateStudentInformation(Student student, String firstName, String lastName, String email) {
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        studentRepository.save(student);
    }

    public void changePassword(Student student, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        student.setPassword(encodedPassword);
        studentRepository.save(student);
    }

    public boolean comparePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
