package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.exception.UserNotFoundException;
import com.bulmansoda.map_community.model.User;
import com.bulmansoda.map_community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), new ArrayList<>());

    }
}
