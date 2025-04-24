package at.fhv.sys.hotel.models;

import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
@Entity
public class BookingQueryPanacheModel extends PanacheEntity {
    public Long bookingId;
    public LocalDateTime fromDate;
    public LocalDateTime toDate;
    public int numberOfPersons;
    public int roomNumber;

    @Enumerated(EnumType.STRING)
    public BookingState state;

}
