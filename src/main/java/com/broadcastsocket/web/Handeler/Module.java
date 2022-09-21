package com.broadcastsocket.web.Handeler;

import com.broadcastsocket.web.Model.Message;
import com.broadcastsocket.web.Model.Rooms;
import com.broadcastsocket.web.Repo.RoomsRepository;
import com.broadcastsocket.web.Service.MessageService;
import com.broadcastsocket.web.Service.RoomService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Component
public class Module {
    private final SocketIOServer server;
    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private MessageService messageService;

    public Module(SocketIOServer server) {
        this.server = server;
        server.addConnectListener(onConnected());
        server.addConnectListener(onRoomCreated());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", Message.class, onChatReceived());
        server.addEventListener("create_room", Rooms.class, onCreatingRoom());

    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, eventName) -> messageService.sendMessageAndSave(data.getRoom(), data.getUserName(), "get_message", senderClient, data.getMessage());
    }

    private ConnectListener onRoomCreated() {
        return createClient -> createClient.get("createRoom");
    }

    private ConnectListener onConnected() {
        return client -> {
            String room = client.getHandshakeData().getSingleUrlParam("room");
            if (room != null) {
                List<Rooms> roomList = roomsRepository.findAll();
                String[] rooms = room.split(",");
                for (String s : rooms) {
                    Optional<Rooms> roomExists = roomList.stream().filter(e -> Objects.equals(e.getRoomName(), s)).findFirst();
                    if (roomExists.isPresent()) {
                        client.joinRoom(s.trim());
                        log.info("Socket ID [{}]  Connected to socket", URLEncoder.encode(client.getSessionId().toString(), StandardCharsets.UTF_8));
                    } else {
                        client.sendEvent("error", "sorry room doesn't exists");
                        client.disconnect();
                    }
                }
            }
        };
    }

    private DataListener<Rooms> onCreatingRoom() {
        return (senderClient, data, eventName) -> roomService.createRoom(data, "get_room", senderClient);
    }

    private DisconnectListener onDisconnected() {
        return client -> log.info("Client[{}] - Disconnected from socket", URLEncoder.encode(client.getSessionId().toString(), StandardCharsets.UTF_8));
    }

    public SocketIOServer getServer() {
        return server;
    }
}