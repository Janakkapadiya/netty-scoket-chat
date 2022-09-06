package com.broadcastsocket.demo.Repo;

import com.broadcastsocket.demo.Model.Channels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channels,Integer>{

}
