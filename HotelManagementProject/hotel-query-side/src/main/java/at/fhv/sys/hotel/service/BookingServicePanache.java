package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.models.BookingQueryPanacheModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
@ApplicationScoped
public class BookingServicePanache {
    public List<BookingQueryPanacheModel> getAllBookings(){return BookingQueryPanacheModel.listAll();}

    @Transactional
    public void createBooking(BookingQueryPanacheModel booking){booking.persist();}
}
