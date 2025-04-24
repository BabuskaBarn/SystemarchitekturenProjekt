package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;

import at.fhv.sys.hotel.models.BookingQueryModel;
import at.fhv.sys.hotel.projection.BookingProjection;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;


@Path("/api/query")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingQueryController {
    private static final Logger LOG = Logger.getLogger(BookingQueryController.class);

    @Inject
    BookingProjection bookingProjection;

    @Inject
    EntityManager entityManager;

    public BookingQueryController(){}
    @POST
    @Path("/bookingCreated")
    public Response bookingCreated(BookingCreated event) {
        LOG.infof("Received booking created event: %s", event);
        bookingProjection.processBookingCreatedEvent(event);
        return Response.ok(event).build();
    }
    @GET
    @Path("/bookings")
    public Response getBookingsByDateRange(
            @QueryParam("from") String fromDate,
            @QueryParam("to") String toDate) {

        try {
            // Parameter validieren
            if (fromDate == null || fromDate.isEmpty() || toDate == null || toDate.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Both 'from' and 'to' date parameters are required")
                        .build();
            }

            LocalDateTime startDateTime = LocalDateTime.parse(fromDate);
            LocalDateTime endDateTime = LocalDateTime.parse(toDate);

            if (startDateTime.isAfter(endDateTime)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Start date must be before end date")
                        .build();
            }

            // Buchungen abfragen
            List<BookingQueryModel> bookings = entityManager.createQuery(
                            "SELECT b FROM BookingQueryModel b " +
                                    "WHERE (b.fromDate BETWEEN :start AND :end) " +
                                    "OR (b.toDate BETWEEN :start AND :end) " +
                                    "OR (b.fromDate <= :start AND b.toDate >= :end)",
                            BookingQueryModel.class)
                    .setParameter("start", startDateTime)
                    .setParameter("end", endDateTime)
                    .getResultList();

            return Response.ok(bookings).build();

        } catch (DateTimeParseException e) {
            LOG.error("Invalid date format received", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format. Please use ISO-8601 format (e.g., 2023-01-01T10:00:00)")
                    .build();
        } catch (Exception e) {
            LOG.error("Error fetching bookings", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching bookings")
                    .build();
        }
    }

    @POST
    @Path("/bookingCancelled")
    public Response bookingCancelled(BookingCancelled event) {
        LOG.infof("Received booking cancelled event: %s", event);
        bookingProjection.processBookingCancelledEvent(event);
        return Response.ok(event).build();
    }
}
