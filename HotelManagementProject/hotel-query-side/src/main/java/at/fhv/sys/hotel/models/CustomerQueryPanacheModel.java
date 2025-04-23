package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class CustomerQueryPanacheModel extends PanacheEntity {

    // Panache provides Auto-CRUD for everything
    public Long userId;
    public String email;
    public String address;
    public String name;


}
