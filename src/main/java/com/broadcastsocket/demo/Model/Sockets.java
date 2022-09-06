package com.broadcastsocket.demo.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sockets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sc_id", nullable = false)
    private Long id;
    private String sockets;

    @ManyToMany
    @JoinTable(name = "channel_clients",joinColumns = {@JoinColumn(name = "ch_id")},inverseJoinColumns = {@JoinColumn(name = "skt_id")})
    private List<Channels> channels;

}
