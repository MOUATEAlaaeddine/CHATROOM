package com.chatroom.futuristicchat.service;

import com.chatroom.futuristicchat.entity.JoinRequest;
import com.chatroom.futuristicchat.entity.User;
import com.chatroom.futuristicchat.repository.ChatRoomRepository;
import com.chatroom.futuristicchat.repository.JoinRequestRepository;
import com.chatroom.futuristicchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JoinRequestRepository joinRequestRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    public User register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(User.Role.STUDENT);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public JoinRequest requestJoin(User user, String roomId) {
        // Validation logic could go here
        var room = chatRoomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        JoinRequest req = new JoinRequest();
        req.setUsername(user.getUsername());
        req.setRoomName(room.getName());
        req.setStatus(JoinRequest.RequestStatus.PENDING);
        return joinRequestRepository.save(req);
    }

    public List<JoinRequest> getUserRequests(String username) {
        return joinRequestRepository.findAll().stream()
                .filter(r -> r.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public List<JoinRequest> getPendingRequests() {
        return joinRequestRepository.findByStatus(JoinRequest.RequestStatus.PENDING);
    }

    public void handleRequest(String requestId, String status) {
        JoinRequest req = joinRequestRepository.findById(requestId).orElse(null);
        if (req != null) {
            req.setStatus(JoinRequest.RequestStatus.valueOf(status));
            joinRequestRepository.save(req);
        }
    }
}
