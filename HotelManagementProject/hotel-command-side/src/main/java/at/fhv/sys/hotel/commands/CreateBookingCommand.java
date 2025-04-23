package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.Enums.BookingState;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateBookingCommand(Long bookingId,
                                   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromDate,
                                   @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toDate,
                                   int numberOfPersons, int roomNumber, BookingState state) {
    public static CreateBookingCommand of(Long bookingId,
                                          LocalDateTime fromDate,
                                          LocalDateTime toDate,
                                          int numberOfPersons,
                                          int roomNumber) {
        return new CreateBookingCommand(bookingId, fromDate, toDate, numberOfPersons, roomNumber, BookingState.Open);
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
