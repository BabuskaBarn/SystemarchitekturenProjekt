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

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class BookingAggregate {
    @Inject
    @RestClient
    EventBusClient eventClient;

    public String handle(CreateBookingCommand command) {
        try {
            // 1. Event erstellen
            BookingCreated event = new BookingCreated(
                    command.bookingId(),
                    command.fromDate(),
                    command.toDate(),
                    command.numberOfPersons(),
                    command.roomNumber()
            );

            // 2. Event an Event Bus senden
            BookingCreated response = eventClient.processBookingCreatedEvent(event);
            LOG.info("Booking event successfully processed: " + response);

            // 3. Bestätigung zurückgeben
            return command.bookingId().toString();

        } catch (Exception e) {
            LOG.error("Failed to process booking creation", e);
            throw new RuntimeException("Booking creation failed", e);
        }
    }

        public String handle (CancelBookingCommand command){
            // Buchungsstornierung verarbeiten
            Logger.getAnonymousLogger().info("Processing cancel booking: " + command.bookingId());

            // BookingCancelled Event erzeugen
            BookingCancelled event = new BookingCancelled(command.bookingId());

            // Event an EventBusClient schicken
            eventClient.processBookingCancelledEvent(event);

            return String.valueOf(command.bookingId());
        }
    }

