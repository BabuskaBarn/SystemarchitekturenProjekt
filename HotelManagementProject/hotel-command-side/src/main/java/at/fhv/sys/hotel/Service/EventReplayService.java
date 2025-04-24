package at.fhv.sys.hotel.Service;


import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import at.fhv.sys.hotel.projection.BookingProjection;
import at.fhv.sys.hotel.projection.CustomerProjection;
import at.fhv.sys.hotel.projection.RoomProjection;
import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.eventstore.dbclient.ReadAllOptions;
import com.eventstore.dbclient.ReadResult;
import com.eventstore.dbclient.ResolvedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class EventReplayService {

    @Inject
    CustomerProjection customerProjection;
    @Inject
    RoomProjection roomProjection;
    @Inject
    BookingProjection bookingProjection;


    private final ObjectMapper objectMapper;

    public EventReplayService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for LocalDate handling
    }

    @PostConstruct
    public void init() {
        try {
            EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow("esdb://localhost:2113?tls=false");
            EventStoreDBClient client = EventStoreDBClient.create(settings);

            ReadAllOptions options = ReadAllOptions.get().forwards().fromStart();

            ReadResult result = client.readAll(options).get(); // :white_check_mark: Blocking call

            for (ResolvedEvent resolvedEvent : result.getEvents()) {
                processEvent(resolvedEvent); // :white_check_mark: Called in correct context
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Ensure this method is transactional
    @Transactional
    public void processEvent(ResolvedEvent event) {
        String eventType = event.getOriginalEvent().getEventType();
        String eventData = new String(event.getOriginalEvent().getEventData(), StandardCharsets.UTF_8);
        System.out.println("Replaying event (CMD): " + eventType);

        try {
            // Process event based on type
            switch (eventType) {
                case "CustomerCreated":
                    CustomerCreated customer = objectMapper.readValue(eventData, CustomerCreated.class);
                    customerProjection.processCustomerCreatedEvent(customer); // Ensures projection is updated in a transactional context
                    break;
                case "RoomCreated":
                    RoomCreated room = objectMapper.readValue(eventData, RoomCreated.class);
                    roomProjection.processRoomCreatedEvent(room); // Ensures projection is updated in a transactional context
                    break;
                case "BookingCreated":
                    BookingCreated bookingCreated = objectMapper.readValue(eventData, BookingCreated.class);
                    bookingProjection.processBookingCreatedEvent(bookingCreated); // Now handling the booking event
                    break;
                default:
                    // Ignore unknown event types
                    System.out.println("Ignoring event type: " + eventType);
            }
        } catch (Exception e) {
            // Log any exceptions that occur during event processing
            e.printStackTrace();
        }
    }
}
/*
import at.fhv.sys.hotel.commands.shared.events.BookingCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.RoomCreated;

import at.fhv.sys.hotel.projection.BookingProjection;
import at.fhv.sys.hotel.projection.CustomerProjection;
import at.fhv.sys.hotel.projection.RoomProjection;
import com.eventstore.dbclient.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;



import com.fasterxml.jackson.databind.SerializationFeature;

import io.quarkus.runtime.StartupEvent;

import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.util.List;


@ApplicationScoped
public class EventReplayService {


    private static final Logger LOG = Logger.getLogger(EventReplayService.class);
    private static final int READ_BATCH_SIZE = 100;

    @Inject
    CustomerProjection customerProjection;

    @Inject
    RoomProjection roomProjection;

    @Inject
    BookingProjection bookingProjection;

    private final ObjectMapper objectMapper;
    private EventStoreDBClient client;

    public EventReplayService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void onStart(@Observes StartupEvent ev) {
        try {
            this.client = EventStoreDBClient.create(
                    EventStoreDBConnectionString.parseOrThrow("esdb://localhost:2113?tls=false"));

            replayAllEvents();
        } catch (Exception e) {
            LOG.error("Failed to initialize EventReplayService", e);
            throw new RuntimeException("Event replay initialization failed", e);
        }
    }

    @Transactional
    public void replayAllEvents() {
        try {
            // CORRECTED: Using the proper start position
            Position position = new Position(0,0);// or whichever works
            boolean hasMore = true;

            while (hasMore) {
                ReadAllOptions options = ReadAllOptions.get()
                        .forwards()
                        .fromPosition(position)
                        .maxCount(READ_BATCH_SIZE);

                ReadResult result = client.readAll(options).get();
                List<ResolvedEvent> events = result.getEvents();

                if (events.isEmpty()) {
                    hasMore = false;
                    continue;
                }

                for (ResolvedEvent event : events) {
                    processEvent(event);
                }
