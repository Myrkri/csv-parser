package com.example.csv.service.impl;

import com.example.csv.repository.UserRepository;
import com.example.csv.repository.entity.UserDetailsEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        final var result = Assertions.assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("user"));
        Assertions.assertEquals("User not found with username: user", result.getMessage());
    }

    @Test
    void testLoadUserByUsername_success() {
        final UserDetailsEntity userDetails = buildEntity();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userDetails));

        final var result = customUserDetailsService.loadUserByUsername("user");
        Assertions.assertEquals(userDetails.getUsername(), result.getUsername());
        Assertions.assertEquals(userDetails.getPassword(), result.getPassword());
        Assertions.assertTrue(result.getAuthorities().toString().contains(userDetails.getRoles()));
        Assertions.assertTrue(userDetails.isEnabled());
    }

    private UserDetailsEntity buildEntity() {
        final UserDetailsEntity user = new UserDetailsEntity();
        user.setId(1L);
        user.setUsername("user");
        user.setEnabled(true);
        user.setPassword("12345");
        user.setRoles("ADMIN");
        return user;
    }
}