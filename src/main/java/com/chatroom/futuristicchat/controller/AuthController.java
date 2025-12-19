package com.chatroom.futuristicchat.controller;

import com.chatroom.futuristicchat.dto.LoginRequest;
import com.chatroom.futuristicchat.dto.UserDTO;
import com.chatroom.futuristicchat.entity.User;
import com.chatroom.futuristicchat.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = userService.authenticate(request.getUsername(), request.getPassword());
        if (user != null) {
            session.setAttribute("user", user);
            return ResponseEntity.ok(new UserDTO(user.getUsername(), user.getRole().name()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Creds");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request, HttpSession session) {
        try {
            User user = userService.register(request.getUsername(), request.getPassword());
            session.setAttribute("user", user);
            return ResponseEntity.ok(new UserDTO(user.getUsername(), user.getRole().name()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return ResponseEntity.ok(new UserDTO(user.getUsername(), user.getRole().name()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
