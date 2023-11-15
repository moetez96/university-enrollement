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

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        return this.courseRepository.findAll();
    }

    public Optional<Course> findCourse(long courseId) {
        return this.courseRepository.findById(courseId);
    }

    //TODO: Enrolls a student in a course
    public void enrollStudent(Course course, Student student){

    }

    //TODO: Enrolls a student in a course
    public void unEnrollStudent(Course course, Student student) {

    }
}
