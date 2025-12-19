package com.chatroom.futuristicchat.repository;

import com.chatroom.futuristicchat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    ChatRoom findByName(String name);
}
