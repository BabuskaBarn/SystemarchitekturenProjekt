package at.fhv.sys.hotel.commands.shared.events;


import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class BookingCancelled {


    private Long bookingId;



    private BookingState state;

    // Konstruktor
    @JsonCreator // Für Jackson, um das Objekt zu deserialisieren
    public BookingCancelled(@JsonProperty("bookingId") Long bookingId) {
        this.bookingId = bookingId;
        this.state = BookingState.Cancelled; // Zustand wird hier festgelegt
    }





    public Long getBookingId() {
        return bookingId;
    }

    public BookingState getState() {
        return state;
    }
}
