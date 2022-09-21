package com.broadcastsocket.web.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Message")
@Setter
@ToString
@Getter
@NoArgsConstructor
public class Message {
    private String userName;
    private MessageType type;
    private String message;

    private String room;

    public Message(MessageType type, String message, String userName) {
        this.type = type;
        this.message = message;
        this.userName = userName;
    }
}

