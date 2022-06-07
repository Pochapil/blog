package ru.bagration.spring.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ApplicationUserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    ResponseEntity<?> createUser(String username, String password);
}
