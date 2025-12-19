package com.chatroom.futuristicchat.repository;

import com.chatroom.futuristicchat.entity.JoinRequest;
import com.chatroom.futuristicchat.entity.Message;
import com.chatroom.futuristicchat.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
