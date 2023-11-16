package com.example.university.service;

import com.example.university.entity.Course;
import com.example.university.entity.Student;
import com.example.university.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoursesService {

    private final CourseRepository courseRepository;

    public CoursesService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return this.courseRepository.findAll();
    }

    public Optional<Course> findCourse(long courseId) {
        return this.courseRepository.findById(courseId);
    }

    public void enrollStudent(Course course, Student student){
        course.addEnrolledStudent(student);
        this.courseRepository.save(course);
    }

    public void unEnrollStudent(Course course, Student student) {
        if (course.getEnrolledStudents().contains(student)) {
            course.removeEnrolledStudent(student);
            this.courseRepository.save(course);
        }
    }
}
