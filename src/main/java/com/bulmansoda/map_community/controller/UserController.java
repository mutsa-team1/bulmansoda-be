package com.bulmansoda.map_community.controller;

import com.bulmansoda.map_community.dto.user_service.CreateUserRequest;
import com.bulmansoda.map_community.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public long join(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @DeleteMapping("/delete")
    public void leave(Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        userService.deleteUser(userId);
    }

    @PutMapping("/name")
    public void changeName(@RequestBody String name, Authentication authentication) {
        Long userId = Long.valueOf(authentication.getName());
        userService.changeName(userId, name);
    }

}
