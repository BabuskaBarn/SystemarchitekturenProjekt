package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

@ApplicationScoped
public class RoomAggregate {
    @Inject
    @RestClient
    EventBusClient eventClient;
    public String handle(CreateRoomCommand command) {
        RoomCreated event= new RoomCreated(command.roomId(), command.roomCapacity(),command.roomNumber());


        Logger.getAnonymousLogger().info(eventClient.processRoomCreatedEvent(event).toString());
        //wir geben dem command einen Long dieser erwartet aber einen String;
        return String.valueOf(command.roomId());
    }
}
