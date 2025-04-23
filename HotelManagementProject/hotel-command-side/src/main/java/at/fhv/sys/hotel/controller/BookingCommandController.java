package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.BookingAggregate;
import at.fhv.sys.hotel.commands.CreateBookingCommand;
import at.fhv.sys.hotel.commands.CreateCustomerCommand;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.annotations.Pos;

import java.time.LocalDateTime;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingCommandController {
    private BookingAggregate bookingAggregate;

    public BookingCommandController(BookingAggregate bookingAggregate) {
        this.bookingAggregate = bookingAggregate;
    }

    @POST
    @Path("/createBooking")
    public String createBooking(CreateBookingCommand command) {
        return bookingAggregate.handle(command);
    }

/*
    //TODO es gibt ein Problem mit localDateTime eventuell muss ich es auf Requestbody umändern DIe obere Methodik sollte funktionieren
    @POST
    @Path("/createBooking")
    public String createBooking(@QueryParam("bookingId") Long bookingId, @QueryParam("fromDate") LocalDateTime fromDate, @QueryParam("toDate") LocalDateTime toDate, @QueryParam("numberOfPersons") int numberOfPersons, @QueryParam("roomNumber") int roomNumber) {
        return bookingAggregate.handle(new CreateBookingCommand(bookingId, fromDate, toDate, numberOfPersons, roomNumber));

    }

 */

    @POST
    @Path(("/cancelBooking"))
    public String cancelBooking(){return null;}

    @POST
    @Path(("/payBooking"))
    public String payBooking(){return null;}
}
