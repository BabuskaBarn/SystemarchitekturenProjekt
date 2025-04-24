package at.fhv.sys.eventbus.controller;

import at.fhv.sys.eventbus.services.EventProcessingService;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.BookingPaid;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerUpdated;
import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logmanager.Logger;

import java.time.LocalDate;
import java.util.List;

@Path("/api/commands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventsController {
    @Inject
    EventProcessingService eventStoreService;


    public EventsController() {
    }

    @POST
    @Path("/customerCreated")
    @Operation(summary = "Empfängt ein CustomerCreated Event")
    @APIResponse(responseCode = "200", description = "Event erfolgreich verarbeitet",
            content = @Content(schema = @Schema(implementation = CustomerCreated.class)))
    public Response customerCreated(CustomerCreated event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("customer-" + event.getUserId(), event);
        return Response.ok(event).build();
    }
    @POST
    @Path("/bookingCreated")
    @Operation(summary = "Empfängt ein BookingCreated Event")
    @APIResponse(responseCode = "200", description = "Event erfolgreich verarbeitet",
            content = @Content(schema = @Schema(implementation = BookingCreated.class)))
    public Response BookingCreated (BookingCreated event){
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("booking-" + event.getBookingId(), event);
        return Response.ok(event).build();}

    @POST
    @Path("/roomCreated")
    @Operation(summary = "Empfängt ein RoomCreated Event")
    @APIResponse(responseCode = "200", description = "Event erfolgreich verarbeitet",
            content = @Content(schema = @Schema(implementation = RoomCreated.class)))
    public Response RoomCreated(RoomCreated event){
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("room-" + event.getRoomId(), event);
        return Response.ok(event).build();
    }

    @POST
    @Path("/bookingCancelled")
    @Operation(summary = "Empfängt ein BookingCancelled Event")
    @APIResponse(responseCode = "200", description = "Event erfolgreich verarbeitet",
            content = @Content(schema = @Schema(implementation = BookingCancelled.class)))
    public Response BookingCancelled(BookingCancelled event){
        Logger.getAnonymousLogger().info("Received event: " + event);
        eventStoreService.processEvent("booking-" + event.getBookingId(), event);
        return Response.ok(event).build();}


    @POST
    @Path("/customerUpdated")
    @Operation(summary = "Empfängt ein CustomerUpdated Event")
    @APIResponse(responseCode = "200", description = "Event erfolgreich verarbeitet",
            content = @Content(schema = @Schema(implementation = CustomerUpdated.class)))
    public Response customerUpdated(CustomerUpdated event) {
        Logger.getAnonymousLogger().info("Received CustomerUpdated event: " + event);
        eventStoreService.processEvent("customer-" + event.getCustomerId(), event);
        return Response.ok(event).build();
    }

    @POST
    @Path("/bookingPaid")
    @Operation(summary = "Empfängt ein BookingPaid Event")
    @APIResponse(responseCode = "200", description = "Zahlung erfolgreich verarbeitet")
    public Response bookingPaid(BookingPaid event) {
        Logger.getAnonymousLogger().info("Zahlung erhalten für Booking: " + event.getBookingId());
        eventStoreService.processEvent("booking-" + event.getBookingId(), event);
        return Response.ok().build();
    }

}
