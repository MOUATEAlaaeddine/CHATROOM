package com.chatroom.futuristicchat.service;

import com.chatroom.futuristicchat.entity.ChatRoom;
import com.chatroom.futuristicchat.entity.Message;
import com.chatroom.futuristicchat.repository.ChatRoomRepository;
import com.chatroom.futuristicchat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    private final Map<String, Set<String>> activeUsers = new ConcurrentHashMap<>();

    public Message sendMessage(String roomId, Message message) {
        message.setRoom(roomId);
        message.setTimestamp(LocalDateTime.now());

        // Handle LEAVE type
        if (message.getType() == Message.MessageType.LEAVE) {
            removeUser(roomId, message.getSender());
        }

        return messageRepository.save(message);
    }

    public Message addUser(String roomId, Message message) {
        message.setRoom(roomId);
        message.setTimestamp(LocalDateTime.now());
        message.setType(Message.MessageType.JOIN);

        activeUsers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(message.getSender());

        return message;
    }

    public void removeUser(String roomId, String username) {
        if (activeUsers.containsKey(roomId)) {
            activeUsers.get(roomId).remove(username);
        }
    }

    public Set<String> getUsersInRoom(String roomId) {
        return activeUsers.getOrDefault(roomId, Collections.emptySet());
    }

    public List<Message> getHistory(String roomId) {
        return messageRepository.findByRoom(roomId);
    }

    public List<ChatRoom> getAllRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom createRoom(String name) {
        ChatRoom room = new ChatRoom();
        room.setName(name);
        return chatRoomRepository.save(room);
    }

    public void deleteRoom(String id) {
        chatRoomRepository.deleteById(id);
    }

    public ChatRoom getRoomById(String id) {
        return chatRoomRepository.findById(id).orElse(null);
    }
}
