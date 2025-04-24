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
import java.util.List;

@ApplicationScoped
public class RoomService {
    @PersistenceContext
    static EntityManager entityManager;

    public List<RoomQueryModel> getAllRooms(){
        return entityManager.createQuery("SELECT c FROM RoomQueryModel c", RoomQueryModel.class).getResultList();
    }
    @Transactional
    public void createRoom(RoomQueryModel room) {
        entityManager.persist(room);
    }


    @Transactional
    public static List<RoomQueryModel> getFreeRooms(LocalDate fromDate, LocalDate toDate) {
        // für gebuchte Zimmer im Zeitraum
        String jpqlBookedRooms = """
        SELECT b.roomNumber FROM BookingQueryModel b
        WHERE (b.fromDate <= :toDate AND b.toDate >= :fromDate)
        AND b.state = 'OPEN'
        """;

        List<Integer> bookedRoomNumbers = entityManager.createQuery(jpqlBookedRooms, Integer.class)
                .setParameter("fromDate", fromDate.atStartOfDay())
                .setParameter("toDate", toDate.atTime(23, 59, 59))
                .getResultList();

        // für freie Zimmer
        String jpqlFreeRooms = """
        SELECT r FROM RoomQueryModel r
        WHERE r.roomNumber NOT IN :bookedRoomNumbers
        """;

        return entityManager.createQuery(jpqlFreeRooms, RoomQueryModel.class)
                .setParameter("bookedRoomNumbers", bookedRoomNumbers.isEmpty() ? List.of(-1) : bookedRoomNumbers)
                .getResultList();
    }

}
