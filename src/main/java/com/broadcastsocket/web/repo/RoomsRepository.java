package com.broadcastsocket.web.repo;

import com.broadcastsocket.web.model.Rooms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends MongoRepository<Rooms,Integer> {
}
