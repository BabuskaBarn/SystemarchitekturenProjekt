package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.CustomerQueryModel;
import at.fhv.sys.hotel.models.RoomQueryModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RoomService {
    @PersistenceContext
    EntityManager entityManager;

    public List<RoomQueryModel> getAllRooms(){
        return entityManager.createQuery("SELECT c FROM RoomQueryModel c", RoomQueryModel.class).getResultList();
    }
    @Transactional
    public void createRoom(RoomQueryModel room) {
        entityManager.persist(room);
    }
}
