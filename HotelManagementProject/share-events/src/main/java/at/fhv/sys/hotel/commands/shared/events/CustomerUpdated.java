package at.fhv.sys.hotel.commands.shared.events;

public class CustomerUpdated {
    private Long customerId;
    private String email;
    private String address;

    public CustomerUpdated(Long customerId, String email, String address) {
        this.customerId = customerId;
        this.email = email;
        this.address = address;
    }

    public CustomerUpdated() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
