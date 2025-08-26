package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.auth.JwtUtil;
import com.bulmansoda.map_community.dto.auth.LoginRequest;
import com.bulmansoda.map_community.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        // 1. Authenticate using name and phone number (as password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getName(), loginRequest.getPhoneNumber())
        );

        // 2. If authentication is successful, find the user to get their ID for the token payload
        final com.bulmansoda.map_community.model.User user = userRepository.findByName(loginRequest.getName())
                .orElseThrow(() -> new Exception("User not found after authentication"));

        // 3. Generate the token with the user's database ID
        final String token = jwtUtil.generateToken(user.getId());

        // 4. Return the token in the response
        return ResponseEntity.ok(Map.of("token", token));
    }
}
