package com.broadcastsocket.demo.Model;

import lombok.*;

@Data
@Getter
@NoArgsConstructor
public class Message {
    private MessageType type;
    private String message;

    private String room;

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }
}

