package com.example.csv.rest;

import com.example.csv.model.AuthDTO;
import com.example.csv.security.util.JwtSupport;
import com.example.csv.service.impl.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LogIn {

    private JwtSupport jwtSupport;
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/me")
    public Object me() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/auth")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthDTO authDTO) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authDTO.getUsername());
        if (userDetails.isEnabled()) {
            return new ResponseEntity<>(jwtSupport.generateToken(userDetails), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Wrong user credentials", HttpStatus.FORBIDDEN);
        }
    }
}
