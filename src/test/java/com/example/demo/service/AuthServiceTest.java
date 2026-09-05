package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService, covering the User Authentication (Login)
 * requirement from the assessment brief. The repository is mocked and a
 * real BCryptPasswordEncoder is used so hash verification is genuinely
 * exercised, not just stubbed out.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private AuthService authService;

    private User adminUser;

    @BeforeEach
    void setUp() {
        adminUser = new User("admin", passwordEncoder.encode("admin123"), "STAFF");
        adminUser.setUserId(1L);
    }

    @Test
    @DisplayName("Login with a valid username and password should succeed and return the user")
    void testAuthenticate_ValidCredentials_ReturnsUser() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));

        Optional<User> result = authService.authenticate("admin", "admin123");

        assertTrue(result.isPresent());
        assertEquals("STAFF", result.get().getRole());
        verify(userRepository, times(1)).findByUsername("admin");
    }

    @Test
    @DisplayName("Login with an invalid password should be rejected")
    void testAuthenticate_InvalidPassword_ReturnsEmpty() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));

        Optional<User> result = authService.authenticate("admin", "wrongpass");

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Login with a non-existent username should be rejected without error")
    void testAuthenticate_NonExistentUsername_ReturnsEmpty() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        Optional<User> result = authService.authenticate("ghost", "anything");

        assertFalse(result.isPresent());
    }
}
