package com.broadcastsocket.web;

import com.broadcastsocket.web.Repo.UserRepository;
import com.broadcastsocket.web.Service.CostumUserdetailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BroadcastsocketApplication {

    private final UserRepository userRepository;

    public BroadcastsocketApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BroadcastsocketApplication.class, args);
    }

    @Bean(name = "userDetails")
    public UserDetailsService getUserDetailsService() {
        return new CostumUserdetailService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
