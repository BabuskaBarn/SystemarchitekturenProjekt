package at.fhv.sys.hotel.commands;

public record CreateRoomCommand(Long roomId,  int roomCapacity, int roomNumber) {
}
