package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import at.fhv.sys.hotel.models.RoomQueryModel;
import at.fhv.sys.hotel.projection.RoomProjection;
import at.fhv.sys.hotel.service.RoomService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/api/query")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomQueryController {
    @Inject
    RoomProjection roomProjection;

    @Inject
    RoomService roomService;

    public RoomQueryController() {

    }

    @POST
    @Path("/roomCreated")
    public Response roomCreated(RoomCreated event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        roomProjection.processRoomCreatedEvent(event);
        return Response.ok(event).build();
    }

    @GET
    @Path("/free")
    public Response getFreeRooms(
            @QueryParam("from") @DefaultValue("2023-01-01T00:00:00") String fromDateStr,
            @QueryParam("to") @DefaultValue("2023-12-31T23:59:59") String toDateStr
    ) {
        try {
            LocalDateTime fromDate = LocalDateTime.parse(fromDateStr);
            LocalDateTime toDate = LocalDateTime.parse(toDateStr);

            if (fromDate.isAfter(toDate)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid date range: 'from' date must be before 'to' date")
                        .build();
            }

            List<RoomQueryModel> freeRooms = roomService.getFreeRooms(fromDate, toDate);
            return Response.ok(freeRooms).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format. Please use ISO-8601 format (e.g., 2023-01-01T00:00:00)")
                    .build();
        }
    }
}
