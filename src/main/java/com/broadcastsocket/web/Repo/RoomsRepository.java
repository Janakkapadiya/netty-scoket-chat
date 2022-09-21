package com.broadcastsocket.web.Repo;

import com.broadcastsocket.web.Model.Rooms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends MongoRepository<Rooms,Integer> {
}
