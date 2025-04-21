package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.generator.BookingReferenceGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

@ApplicationScoped
public class BookingAggregate {
    @Inject
    @RestClient
    EventBusClient eventClient;
    String bookingReference = BookingReferenceGenerator.generate();
     public String handle(CreateBookingCommand command) {
         BookingCreated event= new BookingCreated(command.bookingId(), command.fromDate(),command.toDate(),command.numberOfPersons(), command.roomNumber(), bookingReference,command.customerId());


         Logger.getAnonymousLogger().info(eventClient.processBookingCreatedEvent(event).toString());
         //wir geben dem command einen Long dieser erwartet aber einen String;
         return String.valueOf(command.bookingId());
     }
}
