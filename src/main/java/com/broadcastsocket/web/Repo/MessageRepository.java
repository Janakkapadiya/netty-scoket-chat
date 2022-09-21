package com.broadcastsocket.web.Repo;

import com.broadcastsocket.web.Model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message,Integer>{

}
