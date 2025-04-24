package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.Enums.BookingState;

public record CancelBookingCommand(Long bookingId) {
}
