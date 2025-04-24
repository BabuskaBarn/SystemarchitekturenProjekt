package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;

import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class RoomAggregate {
    @Inject
    @RestClient
    EventBusClient eventClient;

    public String handle(CreateRoomCommand command) {
        try {
            // 1. Event erstellen
            RoomCreated event = new RoomCreated(
                    command.roomId(),
                    command.roomCapacity(),
                    command.roomNumber()
            );

            // 2. Event an Event Bus senden
            RoomCreated response = eventClient.processRoomCreatedEvent(event);
            LOG.info("Event successfully processed: " + response);

            // 3. Bestätigung zurückgeben
            return String.valueOf(command.roomId());

        } catch (Exception e) {
            LOG.error("Failed to process room creation", e);
            throw new RuntimeException("Room creation failed", e);
        }
    }
}
