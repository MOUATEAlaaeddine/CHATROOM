package com.chatroom.futuristicchat.controller;

import com.chatroom.futuristicchat.dto.ChatRoomDTO;
import com.chatroom.futuristicchat.dto.JoinRequestDTO;
import com.chatroom.futuristicchat.dto.UserDTO;
import com.chatroom.futuristicchat.entity.ChatRoom;
import com.chatroom.futuristicchat.entity.User;
import com.chatroom.futuristicchat.service.ChatService;
import com.chatroom.futuristicchat.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && user.getRole() == User.Role.ADMIN;
    }

    @GetMapping("/dashboard-data")
    public ResponseEntity<?> getDashboardData(HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Map<String, Object> data = new HashMap<>();

        List<UserDTO> users = userService.getAllUsers().stream()
                .map(u -> new UserDTO(u.getUsername(), u.getRole().name()))
                .collect(Collectors.toList());

        List<ChatRoomDTO> rooms = chatService.getAllRooms().stream()
                .map(r -> new ChatRoomDTO(r.getId(), r.getName()))
                .collect(Collectors.toList());

        List<JoinRequestDTO> requests = userService.getPendingRequests().stream()
                .map(r -> new JoinRequestDTO(r.getId(), r.getUsername(), r.getRoomName(), r.getStatus().name()))
                .collect(Collectors.toList());

        data.put("users", users);
        data.put("rooms", rooms);
        data.put("requests", requests);

        return ResponseEntity.ok(data);
    }

    @PostMapping("/create-room")
    public ResponseEntity<?> createRoom(@RequestParam String name, HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        ChatRoom room = chatService.createRoom(name);
        return ResponseEntity.ok(new ChatRoomDTO(room.getId(), room.getName()));
    }

    @PostMapping("/delete-room")
    public ResponseEntity<?> deleteRoom(@RequestParam String id, HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        chatService.deleteRoom(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/handle-request")
    public ResponseEntity<?> handleRequest(@RequestParam String requestId, @RequestParam String status,
            HttpSession session) {
        if (!isAdmin(session))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        userService.handleRequest(requestId, status);
        return ResponseEntity.ok().build();
    }
}
