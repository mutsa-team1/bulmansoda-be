package com.bulmansoda.map_community.service;

import com.bulmansoda.map_community.dto.user_service.CreateUserRequest;
import com.bulmansoda.map_community.exception.UserNotFoundException;
import com.bulmansoda.map_community.model.User;
import com.bulmansoda.map_community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(user);

        return user.getId();
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    public void changeName(Long userId, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setName(name);
    }


}
