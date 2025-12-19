package com.chatroom.futuristicchat.dto;

public class ChatRoomDTO {
    private String id;
    private String name;

    public ChatRoomDTO() {
    }

    public ChatRoomDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
