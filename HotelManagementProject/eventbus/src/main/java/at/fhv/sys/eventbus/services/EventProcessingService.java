package at.fhv.sys.eventbus.services;

import at.fhv.sys.eventbus.client.QueryClient;
import at.fhv.sys.hotel.commands.shared.events.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EventProcessingService {
    private static final Logger LOG = Logger.getLogger(EventProcessingService.class);

    @Inject
    EventStoreService eventStore;

    @Inject
    @RestClient
    QueryClient queryClient;

    public void processEvent(String stream, Object event) {
        try {
            // 1. Validate and log
            if (event == null) {
                throw new IllegalArgumentException("Event cannot be null");
            }
            LOG.infof("Processing %s event for stream %s",
                    event.getClass().getSimpleName(), stream);

            // 2. Store event
            eventStore.storeEvent(stream, event);

            // 3. Forward to query service
            forwardToQueryService(event);

        } catch (Exception e) {
            LOG.errorf("Event processing failed: %s", e.getMessage());
            throw new RuntimeException("Event processing failed", e);
        }
    }
//TODO Booking
    private void forwardToQueryService(Object event) {
        try {
            if (event instanceof CustomerCreated evt) {
                queryClient.forwardCustomerCreatedEvent(evt);
            } else if (event instanceof RoomCreated evt) {
                queryClient.forwardRoomCreatedEvent(evt);
            }else if (event instanceof BookingCreated evt) {
                queryClient.forwardBookingCreatedEvent(evt);

            }
            // Add other event types as needed
        } catch (Exception e) {
            throw new RuntimeException("Failed to forward event to query service", e);
        }
    }
}