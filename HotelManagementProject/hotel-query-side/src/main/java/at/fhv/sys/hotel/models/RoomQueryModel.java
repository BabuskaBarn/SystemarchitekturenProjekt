package at.fhv.sys.hotel.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class RoomQueryModel {


    @Id
    private Long roomId;
    private int roomCapacity;
    private int roomNumber;

    public RoomQueryModel(Long roomId, int roomCapacity, int roomNumber){
        this.roomId=roomId;
        this.roomCapacity=roomCapacity;
        this.roomNumber=roomNumber;

    }




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

    public Long getId() {
        return roomId;
    }



}
