package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.find;
import static io.quarkus.hibernate.orm.panache.PanacheEntityBase.update;

@ApplicationScoped
public class BookingServicePanache {
    public List<BookingQueryPanacheModel> getAllBookings(){return BookingQueryPanacheModel.listAll();}

    @Transactional
    public void createBooking(BookingQueryPanacheModel booking){booking.persist();}

    public BookingQueryPanacheModel findByBookingId(Long bookingId) {
        return BookingQueryPanacheModel.find("bookingId", bookingId).firstResult();
    }

    @Transactional
    public void updateBooking(BookingQueryPanacheModel booking) {
        Panache.getEntityManager().merge(booking);
    }

    // Alternative Update-Methode mit direkter Abfrage
    @Transactional
    public void updateBookingState(Long bookingId, BookingState state) {
        BookingQueryPanacheModel.update("state = ?1 where bookingId = ?2", state, bookingId);
    }
}
