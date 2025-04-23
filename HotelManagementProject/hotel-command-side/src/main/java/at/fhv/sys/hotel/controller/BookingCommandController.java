package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.BookingAggregate;
import at.fhv.sys.hotel.commands.CancelBookingCommand;
import at.fhv.sys.hotel.commands.CreateBookingCommand;
import at.fhv.sys.hotel.commands.CreateCustomerCommand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.annotations.Pos;

import java.time.LocalDateTime;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Booking Commands", description = "Command-side operations for bookings")
public class BookingCommandController {
    private BookingAggregate bookingAggregate;

    public BookingCommandController(BookingAggregate bookingAggregate) {
        this.bookingAggregate = bookingAggregate;
    }

    @POST
    @Path("/createBooking")
    @Operation(
            summary = "Create a new booking",
            description = "Creates a new hotel booking with the provided booking details."
    )
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Booking created successfully"),
            @APIResponse(responseCode = "400", description = "Invalid input")
    })
    public String createBooking(CreateBookingCommand command) {
        return bookingAggregate.handle(command);
    }



    @POST
    @Path(("/cancelBooking"))
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
