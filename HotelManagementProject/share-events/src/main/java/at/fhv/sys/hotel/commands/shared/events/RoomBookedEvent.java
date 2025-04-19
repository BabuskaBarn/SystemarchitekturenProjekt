package at.fhv.sys.hotel.commands.shared.events;


public class RoomBookedEvent {

    public int roomNumber;
    public String customerId;
    public String startDate;
    public String endDate;

    public RoomBookedEvent() {}

    public RoomBookedEvent(int roomNumber, String customerId, String startDate, String endDate) {
        this.roomNumber = roomNumber;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
