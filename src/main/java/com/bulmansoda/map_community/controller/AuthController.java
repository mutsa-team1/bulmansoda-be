package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login/{userId}")
    public ResponseEntity<?> createAuthenticationToken(@PathVariable Long userId) {
        final String token = jwtUtil.generateToken(userId);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
