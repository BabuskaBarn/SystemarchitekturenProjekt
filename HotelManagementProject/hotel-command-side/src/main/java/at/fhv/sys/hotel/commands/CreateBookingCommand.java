package at.fhv.sys.hotel.commands;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreateBookingCommand {
    private final Long bookingId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime fromDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime toDate;
    private final int numberOfPersons;
    private final int roomNumber;
    private final String bookingReference;
    private final Long customerId;


    public CreateBookingCommand(Long bookingId,
                                @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromDate,
                                @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toDate,
                                int numberOfPersons, int roomNumber, String bookingReference, Long customerId) {
        this.bookingId = bookingId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfPersons = numberOfPersons;
        this.roomNumber = roomNumber;
        this.bookingReference = bookingReference;
        this.customerId = customerId;
    }



    public LocalDateTime fromDate() {
        return fromDate;
    }

    public LocalDateTime toDate() {
        return toDate;
    }

    @Override
    public String toString() {
        return "CreateBookingCommand[" +
                "bookingId=" + bookingId + ", " +
                "fromDate=" + fromDate + ", " +
                "toDate=" + toDate + ", " +
                "numberOfPersons=" + numberOfPersons + ", " +
                "roomNumber=" + roomNumber + ']';
    }

    public Long bookingId() {
        return bookingId;
    }

    public int numberOfPersons() {
        return numberOfPersons;
    }

    public int roomNumber() {
        return roomNumber;
    }

    public Long customerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CreateBookingCommand) obj;
        return Objects.equals(this.bookingId, that.bookingId) &&
                Objects.equals(this.fromDate, that.fromDate) &&
                Objects.equals(this.toDate, that.toDate) &&
                this.numberOfPersons == that.numberOfPersons &&
                this.roomNumber == that.roomNumber &&
                Objects.equals(this.customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, fromDate, toDate, numberOfPersons, roomNumber, customerId);
    }


}
