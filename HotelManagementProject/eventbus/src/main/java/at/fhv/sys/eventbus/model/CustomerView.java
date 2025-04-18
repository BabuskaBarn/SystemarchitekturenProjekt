package at.fhv.sys.eventbus.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class CustomerView extends PanacheEntity {

    public Long customerId;
    public String email;
    public String address;
}
