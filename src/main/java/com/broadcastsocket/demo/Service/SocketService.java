package com.broadcastsocket.demo.Service;

import com.broadcastsocket.demo.Model.Channels;
import com.broadcastsocket.demo.Model.Message;
import com.broadcastsocket.demo.Model.MessageType;
import com.broadcastsocket.demo.Model.Sockets;
import com.broadcastsocket.demo.Repo.ChannelRepository;
import com.broadcastsocket.demo.Repo.SocketsRepository;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SocketService {
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private SocketsRepository socketsRepository;

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, String message) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if(!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName,
                        new Message(MessageType.SERVER, message));
            } else {
                client.sendEvent(eventName, new Message(MessageType.SERVER, message));
            }
        }
    }
    public void rooms(String room) {
        Channels channel = new Channels();
        channel.setRoom(room);
        channelRepository.save(channel);
    }
    public void sockets(SocketIOClient senderClient, String room, String eventName, String message) {
        List<Channels> ch = new ArrayList<>();
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            ch.add((Channels) client);
            Sockets socket = new Sockets();
            socket.setChannels(ch);
            client.sendEvent(eventName, new Message(MessageType.SERVER, message));
            socketsRepository.save(socket);
        }
    }
}