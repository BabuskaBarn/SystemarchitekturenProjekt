package at.fhv.sys.hotel.commands.shared.events;

import java.util.UUID;

public class RoomCreated {

    private Long roomId;
    private int roomCapacity;
    private int roomNumber;
    public RoomCreated(Long roomId, int roomCapacity, int roomNumber){
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
        public void setRoomId(Long id) {
            this.roomId = id;
        }
        public Long getRoomId() {
            return roomId;
        }
}


