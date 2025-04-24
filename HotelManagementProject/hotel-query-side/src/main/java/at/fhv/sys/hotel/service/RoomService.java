package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.CustomerQueryModel;
import at.fhv.sys.hotel.models.RoomQueryModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Inheritance;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class RoomService {
    @Inject
    EntityManager entityManager;

    public List<RoomQueryModel> getAllRooms(){
        return entityManager.createQuery("SELECT c FROM RoomQueryModel c", RoomQueryModel.class).getResultList();
    }
    @Transactional
    public void createRoom(RoomQueryModel room) {
        entityManager.persist(room);
    }


    @Transactional
    public List<RoomQueryModel> getFreeRooms(LocalDateTime fromDate, LocalDateTime toDate) {
        // 1. Find all room numbers that are booked in the given time period
        String jpqlBookedRooms = """
            SELECT DISTINCT b.roomNumber 
            FROM BookingQueryModel b
            WHERE (b.fromDate <= :toDate AND b.toDate >= :fromDate)
            AND b.state = 'OPEN'
            """;

        List<Integer> bookedRoomNumbers = entityManager.createQuery(jpqlBookedRooms, Integer.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList();

        // 2. Get all rooms whose numbers are NOT in the booked rooms list
        String jpqlFreeRooms = """
            SELECT r 
            FROM RoomQueryModel r
            WHERE r.roomNumber NOT IN :bookedRoomNumbers
            """;

        return entityManager.createQuery(jpqlFreeRooms, RoomQueryModel.class)
                .setParameter("bookedRoomNumbers", bookedRoomNumbers.isEmpty() ? Collections.emptyList() : bookedRoomNumbers)
                .getResultList();
    }

}
