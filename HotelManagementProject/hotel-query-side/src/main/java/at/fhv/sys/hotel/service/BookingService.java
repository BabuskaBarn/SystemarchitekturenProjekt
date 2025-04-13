package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.CustomerQueryModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BookingService {
    @PersistenceContext
    EntityManager entityManager;

    public List<BookingQueryModel>getAllBookings(){
        return entityManager.createQuery("SELECT c FROM BookingQueryModel c", BookingQueryModel.class).getResultList();
    }

    @Transactional
    public void createBooking(BookingQueryModel booking){entityManager.persist(booking);}
}
