package com.example.Systemarchitekturen.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Rooms {

    @Id
    private UUID roomId;

    private int roomnumber;
    private int roomSize;

    public Rooms() {
    }

    public Rooms(UUID roomId, int roomnumber, int roomSize) {
        this.roomId = roomId;
        this.roomnumber = roomnumber;
        this.roomSize = roomSize;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public int getRoomnumber() {
        return roomnumber;
    }

    public void setRoomnumber(int roomnumber) {
        this.roomnumber = roomnumber;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }
}
