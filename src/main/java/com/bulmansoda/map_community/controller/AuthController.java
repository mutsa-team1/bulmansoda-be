package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.auth.JwtUtil;
import com.bulmansoda.map_community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login/{userId}")
    public ResponseEntity<?> createAuthenticationToken(@PathVariable Long userId) {
        final String token = jwtUtil.generateToken(userId);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
