package com.broadcastsocket.web.service;

import com.broadcastsocket.web.model.Message;
import com.broadcastsocket.web.model.MessageType;
import com.broadcastsocket.web.repo.MessageRepository;
import com.broadcastsocket.web.repo.RoomsRepository;
import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RoomsRepository roomsRepository;

    public void sendMessageAndSave(String room, String userName, String eventName, SocketIOClient senderClient, String message) {
        String rooms = senderClient.getHandshakeData().getSingleUrlParam("room");

        String[] s = rooms.split(",");
        if (!Arrays.asList(s).contains(room)) {
            senderClient.sendEvent("error", "sorry can't send message");
            senderClient.disconnect();
        }

        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!compareClientsSessionIds(client, senderClient)) {
                client.sendEvent(eventName, new Message(MessageType.SERVER, message, userName));
            } else {
                client.sendEvent(eventName, new Message(MessageType.SERVER, message, userName));
            }
            messageStoring(message, userName);
        }
    }

    public boolean compareClientsSessionIds(SocketIOClient client, SocketIOClient senderClient) {
        return client.getSessionId().equals(senderClient.getSessionId());
    }

    public void messageStoring(String message, String userName) {
        Message messages = new Message();
        messages.setMessage(message);
        messages.setType(MessageType.SERVER);
        messages.setUserName(userName);
        messageRepository.save(messages);
    }
}

