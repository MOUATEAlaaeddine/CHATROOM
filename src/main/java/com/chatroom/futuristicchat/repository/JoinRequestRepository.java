package com.chatroom.futuristicchat.repository;

import com.chatroom.futuristicchat.entity.JoinRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface JoinRequestRepository extends MongoRepository<JoinRequest, String> {
    List<JoinRequest> findByStatus(JoinRequest.RequestStatus status);

    JoinRequest findByUsernameAndRoomName(String username, String roomName);
}
