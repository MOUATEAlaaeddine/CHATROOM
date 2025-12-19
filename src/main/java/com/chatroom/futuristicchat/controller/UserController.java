package com.chatroom.futuristicchat.controller;

import com.chatroom.futuristicchat.dto.ChatRoomDTO;
import com.chatroom.futuristicchat.dto.DashboardDTO;
import com.chatroom.futuristicchat.dto.JoinRequestDTO;
import com.chatroom.futuristicchat.entity.User;
import com.chatroom.futuristicchat.service.ChatService;
import com.chatroom.futuristicchat.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    private User getSessionUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping("/dashboard-data")
    public ResponseEntity<?> getDashboardData(HttpSession session) {
        User user = getSessionUser(session);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<ChatRoomDTO> rooms = chatService.getAllRooms().stream()
                .map(r -> new ChatRoomDTO(r.getId(), r.getName()))
                .collect(Collectors.toList());

        List<JoinRequestDTO> requests = userService.getUserRequests(user.getUsername()).stream()
                .map(r -> new JoinRequestDTO(r.getId(), r.getUsername(), r.getRoomName(), r.getStatus().name()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new DashboardDTO(rooms, requests));
    }

    @PostMapping("/request-join")
    public ResponseEntity<?> requestJoin(@RequestParam String roomId, HttpSession session) {
        User user = getSessionUser(session);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            return ResponseEntity.ok(userService.requestJoin(user, roomId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
