package com.chatroom.futuristicchat.dto;

import java.util.List;

public class DashboardDTO {
    private List<ChatRoomDTO> rooms;
    private List<JoinRequestDTO> myRequests;

    public DashboardDTO(List<ChatRoomDTO> rooms, List<JoinRequestDTO> myRequests) {
        this.rooms = rooms;
        this.myRequests = myRequests;
    }

    public List<ChatRoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<ChatRoomDTO> rooms) {
        this.rooms = rooms;
    }

    public List<JoinRequestDTO> getMyRequests() {
        return myRequests;
    }

    public void setMyRequests(List<JoinRequestDTO> myRequests) {
        this.myRequests = myRequests;
    }
}
