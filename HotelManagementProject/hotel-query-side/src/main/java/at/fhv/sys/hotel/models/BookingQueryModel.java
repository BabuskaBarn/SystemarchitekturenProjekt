package at.fhv.sys.hotel.models;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import java.time.LocalDateTime;

@Entity
public class BookingQueryModel {
    @Id
    private Long bookingId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime toDate;

    private int numberOfPersons;
    private int roomNumber;

    public BookingQueryModel(Long bookingId, LocalDateTime fromDate, LocalDateTime toDate, int numberOfPersons, int roomNumber){
        this.bookingId=bookingId;
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.numberOfPersons=numberOfPersons;
        this.roomNumber=roomNumber;
    }

    public BookingQueryModel() {

    }


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
