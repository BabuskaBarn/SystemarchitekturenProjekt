package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.service.BookingService;
import at.fhv.sys.hotel.service.BookingServicePanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.logging.Logger;

@ApplicationScoped
public class BookingProjection {

@Inject
    BookingService bookingService;

@Inject
    BookingServicePanache bookingServicePanache;

    public BookingProjection() {}
//TODO wir müssen den roomNumber noch richtig aus den Rooms extrahieren
public void processBookingCreatedEvent(BookingCreated bookingCreatedEvent){
    Logger.getAnonymousLogger().info("Processing event: " + bookingCreatedEvent);
    bookingService.createBooking(new BookingQueryModel(bookingCreatedEvent.getBookingId(),bookingCreatedEvent.getFromDate(),bookingCreatedEvent.getToDate(), bookingCreatedEvent.getNumberOfPersons(),bookingCreatedEvent.getRoomNumber()));
    BookingQueryPanacheModel booking= new BookingQueryPanacheModel();
    booking.bookingId=bookingCreatedEvent.getBookingId();
    booking.fromDate=bookingCreatedEvent.getFromDate();
    booking.toDate=bookingCreatedEvent.getToDate();
    booking.numberOfPersons=bookingCreatedEvent.getNumberOfPersons();
    booking.roomNumber=bookingCreatedEvent.getRoomNumber();
    bookingServicePanache.createBooking(booking);

}



}
