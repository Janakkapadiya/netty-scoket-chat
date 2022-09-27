package com.broadcastsocket.web.Repo;

import com.broadcastsocket.web.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Integer> {
   Optional<User> findByUserName(String userName);
}
