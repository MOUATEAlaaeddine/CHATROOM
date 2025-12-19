package com.chatroom.futuristicchat.controller;

import com.chatroom.futuristicchat.entity.Message;
import com.chatroom.futuristicchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // WebSocket: Handle sending messages
    @MessageMapping("/chat/{roomId}/sendMessage")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(@DestinationVariable String roomId, @Payload Message message) {
        return chatService.sendMessage(roomId, message);
    }

    // WebSocket: Handle joining (just to announce)
    @MessageMapping("/chat/{roomId}/addUser")
    @SendTo("/topic/room/{roomId}")
    public Message addUser(@DestinationVariable String roomId, @Payload Message message) {
        return chatService.addUser(roomId, message);
    }

    // API: Validating permissions to join is done via JoinRequest in UserController
    // This API gets the history
    @GetMapping("/api/history/{roomId}")
    @ResponseBody
    public List<Message> getHistory(@PathVariable String roomId) {
        return chatService.getHistory(roomId);
    }

    @GetMapping("/api/chat/{roomId}/users")
    @ResponseBody
    public java.util.Set<String> getUsersInRoom(@PathVariable String roomId) {
        return chatService.getUsersInRoom(roomId);
    }
}
