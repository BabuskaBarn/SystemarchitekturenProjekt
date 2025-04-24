package at.fhv.sys.hotel.commands.shared.events;


import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;

public class BookingCancelled {
    private final Long bookingId;
    private final BookingState state;

    // Konstruktor
    public BookingCancelled(Long bookingId) {
        this.bookingId = bookingId;
        this.state = BookingState.Cancelled;
    }


    // Getter für die Buchungs-ID
    public Long getBookingId() {
        return bookingId;
    }

    public BookingState getState() {
        return state;
    }
}
