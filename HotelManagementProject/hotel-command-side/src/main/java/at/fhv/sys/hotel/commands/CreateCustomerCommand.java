package at.fhv.sys.hotel.commands;

public record CreateCustomerCommand(Long userId, String name, String email,String address) {

}
