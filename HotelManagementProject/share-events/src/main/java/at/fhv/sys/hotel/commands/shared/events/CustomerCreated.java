package at.fhv.sys.hotel.commands.shared.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerCreated {

    private Long userId;

    private String name;
    private String email;
    private String address;


    public CustomerCreated() {}

    @JsonCreator
    public CustomerCreated(
            @JsonProperty("userId") Long userId,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("name") String name) {
        this.userId = userId;
        this.email = email;
        this.address = address;
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }


    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return "CustomerCreated{" + "userId='" + userId + '\'' + ", email='" + email + '\'' + '}';
    }


}
