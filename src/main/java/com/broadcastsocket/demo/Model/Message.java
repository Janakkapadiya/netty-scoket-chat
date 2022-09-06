package com.broadcastsocket.demo.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@Getter
@NoArgsConstructor
public class Message {

    private String userName;
    private MessageType type;
    private String message;

    private String room;

    public Message(MessageType type, String message) {
        this.type = type;
        this.message = message;
    }
}

