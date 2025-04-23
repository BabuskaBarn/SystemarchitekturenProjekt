package at.fhv.sys.hotel.commands.shared.events;

public class CustomerCreated {

    private Long userId;
    private String email;
    private String address;
    private String name;

    public CustomerCreated() {}

    public CustomerCreated(Long userId, String name, String email, String address) {
        this.userId = userId;
        this.name=name;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerCreated{" + "userId='" + userId + '\'' + ", email='" + email + '\'' + '}';
    }


}
