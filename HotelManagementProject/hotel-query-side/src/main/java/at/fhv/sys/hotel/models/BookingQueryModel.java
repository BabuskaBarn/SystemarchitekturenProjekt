package at.fhv.sys.hotel.models;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import at.fhv.sys.hotel.commands.shared.events.Enums.PaymentOptions;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;


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
    @Enumerated(EnumType.STRING) // Stores the enum as a string in DB
    private BookingState state = BookingState.Open; // Default value
    private PaymentOptions paymentOptions;




    public BookingQueryModel(Long bookingId, LocalDateTime fromDate, LocalDateTime toDate, int numberOfPersons, int roomNumber){
        this.bookingId=bookingId;
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.numberOfPersons=numberOfPersons;
        this.roomNumber=roomNumber;
        this.state=BookingState.Open;
        this.paymentOptions = PaymentOptions.Card;
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

    public BookingState getState() {
        return state;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    public PaymentOptions getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(PaymentOptions paymentOptions) {
        this.paymentOptions = paymentOptions;
    }
}
