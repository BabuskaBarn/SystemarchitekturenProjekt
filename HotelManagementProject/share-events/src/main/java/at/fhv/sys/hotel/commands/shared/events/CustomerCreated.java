package at.fhv.sys.hotel.commands.shared.events;

public class CustomerCreated {

    private Long userId;
    private String email;
    private String address;

    public CustomerCreated() {}

    public CustomerCreated(Long userId, String email, String address) {
        this.userId = userId;
        this.email = email;
        this.address=address;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "CustomerCreated{" + "userId='" + userId + '\'' + ", email='" + email + '\'' + '}';
    }


}
