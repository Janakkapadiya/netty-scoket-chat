package com.broadcastsocket.demo.Repo;

import com.broadcastsocket.demo.Model.Sockets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocketsRepository extends JpaRepository<Sockets,Integer> {

}
