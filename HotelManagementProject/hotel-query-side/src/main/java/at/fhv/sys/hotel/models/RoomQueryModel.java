package at.fhv.sys.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class RoomQueryModel {


    @Id
    private UUID roomId;
    private int roomCapacity;
    private int roomNumber;

    public void setRoomNumber(int num){
        this.roomNumber=num;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomCapacity(int num){
        this.roomCapacity =num;
    }

    public int getRoomCapacity(){
        return roomCapacity;
    }

    public void setId(UUID id) {
        this.roomId = id;
    }

    public UUID getId() {
        return roomId;
    }
}
