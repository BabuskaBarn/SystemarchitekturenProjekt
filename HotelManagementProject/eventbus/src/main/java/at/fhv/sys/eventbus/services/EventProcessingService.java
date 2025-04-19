package at.fhv.sys.eventbus.services;

import at.fhv.sys.eventbus.client.QueryClient;
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.service.EventStoreService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class EventProcessingService {

    @Inject
    @RestClient
    QueryClient queryClient;

    @Inject
    EventStoreService eventStoreService;


    public EventProcessingService() {
    }

    public void processEvent(String stream, Object eventObject) {
        queryClient.forwardCustomerCreatedEvent((CustomerCreated) eventObject);
        // TBD process Events in EventDBStore

        // 1. Event speichern
        eventStoreService.appendEvent(stream, eventObject);

        // 2. Event an Query-API weiterleiten – dispatch je nach Typ
        if (eventObject instanceof CustomerCreated customerCreated) {
            queryClient.forwardCustomerCreatedEvent(customerCreated);
        } else if (eventObject instanceof BookingCreated bookingCreated) {
            queryClient.forwardBookingCreatedEvent(bookingCreated);
        }
    }



}