package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class RoomQueryPanacheModel extends PanacheEntity {

    public Long roomId;

    public int roomNumber;

    public int roomCapacity;

}
