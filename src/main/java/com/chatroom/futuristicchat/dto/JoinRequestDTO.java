package com.chatroom.futuristicchat.dto;

public class JoinRequestDTO {
    private String id;
    private String username;
    private String roomName;
    private String status;

    public JoinRequestDTO() {
    }

    public JoinRequestDTO(String id, String username, String roomName, String status) {
        this.id = id;
        this.username = username;
        this.roomName = roomName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
