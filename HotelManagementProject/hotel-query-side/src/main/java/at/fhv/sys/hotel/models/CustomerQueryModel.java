package at.fhv.sys.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
public class CustomerQueryModel {

    @Id
    private String userId;
    private String email;
    private Date birthDate;

    private String address;

    public CustomerQueryModel() {}

    public CustomerQueryModel(String userId, String email, String adress) {
        this.userId = userId;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
