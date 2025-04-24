package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import at.fhv.sys.hotel.projection.RoomProjection;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

@Path("/api/query")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomQueryController {
    @Inject
    RoomProjection roomProjection;

    public RoomQueryController(){

    }
    @POST
    @Path("/roomCreated")
    public Response roomCreated(RoomCreated event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        roomProjection.processRoomCreatedEvent(event);
        return Response.ok(event).build();
    }
}
