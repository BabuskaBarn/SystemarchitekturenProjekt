package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.projection.BookingProjection;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingQueryController {
    @Inject
    BookingProjection bookingProjection;

    public BookingQueryController(){}
    @POST
    @Path("/bookingCreated")
    public Response bookingCreated(BookingCreated event){
        Logger.getAnonymousLogger().info("Received event: " + event);
        bookingProjection.processBookingCreatedEvent(event);
        return Response.ok(event).build();
    }


}
