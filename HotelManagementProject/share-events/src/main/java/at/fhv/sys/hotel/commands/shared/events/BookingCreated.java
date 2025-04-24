package at.fhv.sys.hotel.commands.shared.events;

import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BookingCreated {
    private Long bookingId;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime toDate;

    private int numberOfPersons;
    private int roomNumber;
    private BookingState state;

    public BookingCreated(Long bookingId, LocalDateTime fromDate, LocalDateTime toDate, int numberOfPersons, int roomNumber){
        this.bookingId=bookingId;
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.numberOfPersons=numberOfPersons;
        this.roomNumber=roomNumber;
        this.state=BookingState.Open;



    }
    public BookingCreated(){}





    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public void setRoomNumber(int roomNumber){
        this.roomNumber=roomNumber;
    }
    public int getRoomNumber() {
        return roomNumber;
    }

    public int getNumberOfPersons(){
        return numberOfPersons;
    }
    public void setNumberOfPersons(int num){
        this.numberOfPersons=num;
    }
}
