package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.BookingCancelled;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.Enums.BookingState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

@ApplicationScoped
public class BookingAggregate {
    @Inject
    @RestClient
    EventBusClient eventClient;
     public String handle(CreateBookingCommand command) {
         BookingCreated event= new BookingCreated(command.bookingId(), command.fromDate(),command.toDate(),command.numberOfPersons(), command.roomNumber());


         Logger.getAnonymousLogger().info(eventClient.processBookingCreatedEvent(event).toString());
         //wir geben dem command einen Long dieser erwartet aber einen String;
         return String.valueOf(command.bookingId());
     }

    public String handle(CancelBookingCommand command) {
        // Buchungsstornierung verarbeiten
        Logger.getAnonymousLogger().info("Processing cancel booking: " + command.bookingId());

        // BookingCancelled Event erzeugen
        BookingCancelled event = new BookingCancelled(command.bookingId());

        // Event an EventBusClient schicken
        eventClient.processBookingCancelledEvent(event);

        return String.valueOf(command.bookingId());
    }
}
