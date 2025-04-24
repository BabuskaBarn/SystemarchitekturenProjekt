package at.fhv.sys.hotel.controller;


import at.fhv.sys.hotel.commands.CreateRoomCommand;
import at.fhv.sys.hotel.commands.RoomAggregate;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/api/commands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomComandController {

    @Inject
    RoomAggregate roomAggregate;



    @POST
    @Path("/roomCreated")
    public Response createRoom(
            @QueryParam("roomNumber") int roomNumber,
            @QueryParam("roomCapacity") int roomCapacity) {

        try {

            Long roomId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

            CreateRoomCommand command = new CreateRoomCommand(
                    roomId,
                    roomCapacity,
                    roomNumber
            );

            String result = roomAggregate.handle(command);

            return Response.ok()
                    .entity("Room created with ID: " + roomId + ", Number: " + roomNumber)
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity("Failed to create room: " + e.getMessage())
                    .build();
        }
    }
}
