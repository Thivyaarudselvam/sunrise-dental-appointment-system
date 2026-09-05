package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Business logic for staff authentication.
 * Extracted from AuthController so the login rule (look up the user,
 * verify the BCrypt hash) sits in the service layer like every other
 * feature, and can be unit tested in isolation with a mocked repository.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Attempts to authenticate a staff member.
     *
     * @param username the submitted username
     * @param password the submitted raw (unhashed) password
     * @return an Optional containing the User if the username exists and the
     *         password matches the stored BCrypt hash; Optional.empty() otherwise.
     */
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }
}
