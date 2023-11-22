package com.example.university.service;
import com.example.university.entity.Student;
import com.example.university.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final StudentService studentService;

    public CustomUserDetailsService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws  UsernameNotFoundException {

        Optional<Student> student = this.studentService.findStudentByEmail(email);

        if (student.isPresent()) {
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_User"));

            return new CustomUserDetails(
                    student.get().getEmail(),
                    student.get().getPassword(),
                    student.get().getId(),
                    authorities
            );
        }

        throw new UsernameNotFoundException("Student with this email doesn't exist");
    }
}
