package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.BookingAggregate;
import at.fhv.sys.hotel.commands.CancelBookingCommand;
import at.fhv.sys.hotel.commands.CreateBookingCommand;
import at.fhv.sys.hotel.commands.CreateCustomerCommand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.annotations.Pos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Path("/api/commands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Booking Commands", description = "Command-side operations for bookings")
public class BookingCommandController {
    private BookingAggregate bookingAggregate;

    public BookingCommandController(BookingAggregate bookingAggregate) {
        this.bookingAggregate = bookingAggregate;
    }

    @POST
    @Path("/bookingCreated")
    @Operation(
            summary = "Create a new booking",
            description = "Creates a new hotel booking with the provided booking details."
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Booking created successfully"),
            @APIResponse(responseCode = "400", description = "Invalid input")
    })
    public Response createBooking(
            @QueryParam("fromDate") String fromDateStr,
            @QueryParam("toDate") String toDateStr,
            @QueryParam("numberOfPersons") int numberOfPersons,
            @QueryParam("roomNumber") int roomNumber) {

        try {
            // Parse date strings
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime fromDate = LocalDateTime.parse(fromDateStr, formatter);
            LocalDateTime toDate = LocalDateTime.parse(toDateStr, formatter);

            // Automatische ID-Generierung
            Long bookingId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

            CreateBookingCommand command = CreateBookingCommand.of(
                    bookingId,
                    fromDate,
                    toDate,
                    numberOfPersons,
                    roomNumber
            );

            String result = bookingAggregate.handle(command);

            return Response.ok()
                    .entity("Booking created with ID: " + bookingId +
                            ", Room: " + roomNumber +
                            ", Dates: " + fromDateStr + " to " + toDateStr)
                    .build();

        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format. Please use yyyy-MM-dd'T'HH:mm:ss")
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Failed to create booking: " + e.getMessage())
                    .build();
        }
    }



    @POST
    @Path(("/bookingCancelled"))
    @Operation(
            summary = "Cancel an existing booking",
            description = "Cancels a hotel booking using a provided booking ID or cancellation command."
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Booking canceled successfully"),
            @APIResponse(responseCode = "404", description = "Booking not found")
    })
    public String cancelBooking(CancelBookingCommand command){return bookingAggregate.handle(command);}

    @POST
    @Path(("/payBooking"))
    @Operation(
            summary = "Mark a booking as paid",
            description = "Processes the payment for a booking. (Currently not implemented.)"
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Booking marked as paid"),
            @APIResponse(responseCode = "501", description = "Not yet implemented")
    })
    public String payBooking(){return null;}
}
