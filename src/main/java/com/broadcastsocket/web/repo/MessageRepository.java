package com.broadcastsocket.web.repo;

import com.broadcastsocket.web.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message,Integer>{

}
