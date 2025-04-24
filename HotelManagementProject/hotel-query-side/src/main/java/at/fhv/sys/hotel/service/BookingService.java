package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.commands.shared.events.BookingPaid;
import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import at.fhv.sys.hotel.commands.shared.events.Enums.PaymentOptions;
import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.CustomerQueryModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.logging.Logger;

import java.util.List;

@ApplicationScoped
public class BookingService {
    private static final Logger LOG = Logger.getLogger(BookingService.class.getName());
    @PersistenceContext
    EntityManager entityManager;

    public List<BookingQueryModel>getAllBookings(){
        return entityManager.createQuery("SELECT c FROM BookingQueryModel c", BookingQueryModel.class).getResultList();
    }

    @Transactional
    public void createBooking(BookingQueryModel booking){entityManager.persist(booking);}

    public BookingQueryModel findByBookingId(Long bookingId) {
        try {
            return entityManager.find(BookingQueryModel.class, bookingId);
        } catch (Exception e) {
            LOG.severe("Error finding booking: " + e.getMessage());
            return null;
        }
    }

    public void updateBooking(BookingQueryModel booking) {
        try {
            entityManager.merge(booking);
        } catch (Exception e) {
            LOG.severe("Error updating booking: " + e.getMessage());
            throw new RuntimeException("Failed to update booking", e);
        }
    }

    @Transactional
    public void updateBookingPayment(Long bookingId, PaymentOptions paymentMethod, double amount) {
        BookingQueryModel booking = entityManager.find(BookingQueryModel.class, bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("Booking nicht gefunden: " + bookingId);
        }
        if (booking.getState() != BookingState.Open) {
            throw new IllegalStateException("Buchung ist bereits bezahlt/storniert");
        }

        booking.setState(BookingState.Paid);
        booking.setPaymentOptions(paymentMethod);

        entityManager.merge(booking);
    }
}
