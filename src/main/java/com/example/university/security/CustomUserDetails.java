package com.example.university.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUserDetails extends User {

    private final String email;

    public CustomUserDetails(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
