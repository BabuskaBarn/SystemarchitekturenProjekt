package at.fhv.sys.hotel.controller;


import at.fhv.sys.hotel.commands.CreateRoomCommand;
import at.fhv.sys.hotel.commands.RoomAggregate;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomComandController {

    private RoomAggregate roomAggregate;

    @POST
    @Path("/createRoom")
    public String createRoom(@QueryParam("roomId") Long roomId, @QueryParam("roomCapacity") int roomCapacity, @QueryParam("roomNumber") int roomNumber) {
        return roomAggregate.handle(new CreateRoomCommand(roomId, roomCapacity, roomNumber));
    }
}
