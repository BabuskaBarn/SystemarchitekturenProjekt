package at.fhv.sys.hotel.commands;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateBookingCommand(Long bookingId,
                                   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromDate,
                                   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toDate,
                                   int numberOfPersons, int roomNumber) {
    public CreateBookingCommand(Long bookingId, LocalDateTime fromDate, LocalDateTime toDate, int numberOfPersons, int roomNumber) {
        this.bookingId = bookingId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfPersons = numberOfPersons;
        this.roomNumber = roomNumber;
    }

    @Override
    public LocalDateTime fromDate() {
        return fromDate;
    }

    @Override
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

}
