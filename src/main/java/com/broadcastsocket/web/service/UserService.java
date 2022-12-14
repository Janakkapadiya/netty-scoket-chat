package com.broadcastsocket.web.service;

import com.broadcastsocket.web.model.User;
import com.broadcastsocket.web.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String password)
    {
        User user = new User();
        user.setUserName(username);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
