package com.broadcastsocket.web.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "Rooms")
@Getter
@Setter
@NoArgsConstructor
public class Rooms {
    @Id
    private Integer roomId;
    private RoomType roomType;
    @Indexed(unique=true,background = true)
    private String roomName;

    public Rooms(RoomType roomType, String roomName) {
        this.roomType = roomType;
        this.roomName = roomName;
    }
}
