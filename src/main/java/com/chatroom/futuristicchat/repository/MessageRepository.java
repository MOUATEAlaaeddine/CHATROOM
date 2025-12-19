package com.chatroom.futuristicchat.repository;

import com.chatroom.futuristicchat.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoom(String room);
}
