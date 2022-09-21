package com.broadcastsocket.web.Service;

import com.broadcastsocket.web.Model.RoomType;
import com.broadcastsocket.web.Model.Rooms;
import com.broadcastsocket.web.Repo.RoomsRepository;
import com.corundumstudio.socketio.SocketIOClient;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoWriteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomService {

    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public void createRoom(Rooms rooms, String eventName, SocketIOClient senderClient) {
        try {
            String[] roomName = new String[]{rooms.getRoomName()};

            Query query = new Query();
            query.addCriteria(Criteria.where("roomName").in(rooms.getRoomName()));

            boolean roomAlreadyExists = mongoTemplate.exists(query, Rooms.class);

            if (!roomAlreadyExists) {
                Rooms room = new Rooms();
                room.setRoomName(rooms.getRoomName());
                roomsRepository.save(room);
                senderClient.sendEvent(eventName, new Rooms(RoomType.SERVER, rooms.getRoomName()));
            } else {
                senderClient.sendEvent("error", "room name already exists");
            }
        } catch (MongoCommandException | MongoWriteException ex) {
            log.info("Failed to create", ex);
            senderClient.sendEvent("error", ex);
        }
    }
}

