package at.fhv.sys.hotel.commands.shared.events;

import java.util.UUID;

public class RoomCreated {

    private UUID roomId;
    private int roomCapacity;
    private int roomNumber;
    public RoomCreated(UUID roomId, int roomCapacity, int roomNumber){
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
        public void setId(UUID id) {
            this.roomId = id;
        }
        public UUID getId() {
            return roomId;
        }
}


