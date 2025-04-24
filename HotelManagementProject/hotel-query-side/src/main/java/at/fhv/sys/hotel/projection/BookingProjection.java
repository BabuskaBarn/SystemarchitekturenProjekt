package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingPaid;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import at.fhv.sys.hotel.service.BookingService;
import at.fhv.sys.hotel.service.BookingServicePanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class BookingProjection {

    @Inject
    BookingService bookingService;

    @Inject
    BookingServicePanache bookingServicePanache;

    public BookingProjection() {
    }

    //TODO wir müssen den roomNumber noch richtig aus den Rooms extrahieren
    public void processBookingCreatedEvent(BookingCreated bookingCreatedEvent) {
        Logger.getAnonymousLogger().info("Processing event: " + bookingCreatedEvent);
        bookingService.createBooking(new BookingQueryModel(bookingCreatedEvent.getBookingId(), bookingCreatedEvent.getFromDate(), bookingCreatedEvent.getToDate(), bookingCreatedEvent.getNumberOfPersons(), bookingCreatedEvent.getRoomNumber()));
        BookingQueryPanacheModel booking = new BookingQueryPanacheModel();
        booking.bookingId = bookingCreatedEvent.getBookingId();
        booking.fromDate = bookingCreatedEvent.getFromDate();
        booking.toDate = bookingCreatedEvent.getToDate();
        booking.numberOfPersons = bookingCreatedEvent.getNumberOfPersons();
        booking.roomNumber = bookingCreatedEvent.getRoomNumber();
        bookingServicePanache.createBooking(booking);

    }




    @Transactional
    public void processBookingCancelledEvent(BookingCancelled event) {
        LOG.infof("Processing cancellation for booking: %d", event.getBookingId());

        // 1. JPA Model Update
        try {
            BookingQueryModel jpaBooking = bookingService.findByBookingId(event.getBookingId());
            if (jpaBooking != null) {
                jpaBooking.setState(event.getState());
                bookingService.updateBooking(jpaBooking);
                LOG.infof("JPA booking %d updated to state: %s",
                        event.getBookingId(), event.getState());
            } else {
                LOG.warnf("JPA booking not found: %d", event.getBookingId());
            }
        } catch (Exception e) {
            LOG.errorf("JPA update failed for booking %d: %s",
                    event.getBookingId(), e.getMessage());
        }

        // 2. Panache Model Update
        try {
            BookingQueryPanacheModel panacheBooking =
                    bookingServicePanache.findByBookingId(event.getBookingId());

            if (panacheBooking != null) {
                panacheBooking.state = event.getState();
                bookingServicePanache.updateBooking(panacheBooking);
                LOG.infof("Panache booking %d updated to state: %s",
                        event.getBookingId(), event.getState());
            } else {
                LOG.warnf("Panache booking not found: %d", event.getBookingId());
            }
        } catch (Exception e) {
            LOG.errorf("Panache update failed for booking %d: %s",
                    event.getBookingId(), e.getMessage());
        }
    }

}
