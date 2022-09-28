package com.broadcastsocket.web.Controller;

import com.broadcastsocket.web.Service.UserService;
import com.broadcastsocket.web.dto.UserDto;
import com.broadcastsocket.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public User createUser(@RequestBody UserDto user)
    {
        return userService.createUser(user.getUserName(), user.getPassword());
    }
}
