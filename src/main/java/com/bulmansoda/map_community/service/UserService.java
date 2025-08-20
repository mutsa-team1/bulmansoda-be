package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.user_service.ChangeNameRequest;
import com.bulmansoda.map_community.dto.user_service.CreateUserRequest;
import com.bulmansoda.map_community.model.User;
import com.bulmansoda.map_community.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        userRepository.save(user);

        return user.getId();
    }

    public void deleteUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        userRepository.delete(user);
    }

    public void changeName(ChangeNameRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        user.setName(request.getName());
    }


}
