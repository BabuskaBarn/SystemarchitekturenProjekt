package at.fhv.sys.eventbus.services;

import at.fhv.sys.eventbus.client.QueryClient;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import com.eventstore.dbclient.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.UUID;

@ApplicationScoped
public class EventProcessingService  {

    @Inject
    @RestClient
    QueryClient queryClient;


     private EventStoreDBClient eventStoreClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void processEvent(String stream, Object eventObject) {
        try {
            String eventType = eventObject.getClass().getSimpleName();
            String eventJson = objectMapper.writeValueAsString(eventObject);

            EventData eventData = EventData.builderAsJson(UUID.randomUUID(), eventType, eventJson).build();

            eventStoreClient.appendToStream(stream, eventData).get();

            // Optional: Weiterleitung an Query-Side
            if (eventObject instanceof CustomerCreated customerCreated) {
                queryClient.forwardCustomerCreatedEvent(customerCreated);
            } else if (eventObject instanceof RoomCreated roomCreated) {
                queryClient.forwardRoomCreatedEvent(roomCreated);
            } else if (eventObject instanceof BookingCreated bookingCreated) {
                queryClient.forwardBookingCreatedEvent(bookingCreated);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Logging oder Fehlerbehandlung einbauen
        }
    }
}
