package com.example.university.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CustomUserDetails extends User {

    private final String email;
    private final long id;

    public CustomUserDetails(String email, String password, long id, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public long getId() {
        return id;
    }
}
