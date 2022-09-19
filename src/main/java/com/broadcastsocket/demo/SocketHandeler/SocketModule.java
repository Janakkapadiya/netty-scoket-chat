package com.broadcastsocket.demo.SocketHandeler;

import com.broadcastsocket.demo.Model.Message;
import com.broadcastsocket.demo.Model.Sockets;
import com.broadcastsocket.demo.Repo.ChannelRepository;
import com.broadcastsocket.demo.Repo.SocketsRepository;
import com.broadcastsocket.demo.Service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class SocketModule{


    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());

    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, eventName) -> {
            log.info(data.toString());
            socketService.sendMessage(data.getRoom(), "get_message", senderClient, data.getMessage());
        };
    }

    private ConnectListener onConnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");  // paraEnds
            String[] rooms = room.split(",");
            for(String s : rooms)
            {
                socketService.rooms(s);
                client.joinRoom(s);
            }
            log.info("Socket ID [{}]  Connected to socket", URLEncoder.encode(client.getSessionId().toString(),StandardCharsets.UTF_8));
        };
    }


    private DisconnectListener onDisconnected() {
        return client -> log.info("Client[{}] - Disconnected from socket", URLEncoder.encode(client.getSessionId().toString(), StandardCharsets.UTF_8));
    }

    public SocketIOServer getServer() {
        return server;
    }
}