package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
@Entity
public class BookingQueryPanacheModel extends PanacheEntity {
    public Long bookingId;
    public LocalDateTime fromDate;
    public LocalDateTime toDate;
    public int numberOfPersons;
    public int roomNumber;
}
