package at.fhv.sys.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.time.LocalDate;

@Entity
public class CustomerQueryModel {

    @Id
    private Long userId;
    private String email;
    private LocalDate birthdate;
    private String name;

    private String address;

    public CustomerQueryModel() {}

    public CustomerQueryModel(Long userId, String name, String email, String adress) {
        this.userId = userId;
        this.name=name;
        this.email = email;
        this.address =adress;
    }
    public String getUserAddress(){return address;}


    public String getUserId() {
        return "Customer-" + userId;
    }

    public String getEmail() {
        return "Customer Email is: " + email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
}
