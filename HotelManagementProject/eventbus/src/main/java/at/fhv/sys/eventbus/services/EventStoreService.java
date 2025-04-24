package at.fhv.sys.eventbus.services;

import at.fhv.sys.eventbus.Exceptions.EventStoreOperationException;
import com.eventstore.dbclient.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;


@ApplicationScoped
public class EventStoreService {
    private static final Logger LOG = Logger.getLogger(EventStoreService.class);

    private final EventStoreDBClient client;
    private ObjectMapper objectMapper = new ObjectMapper();

    public EventStoreService() {
        // Direct initialization without config class
        Endpoint endpoint = new Endpoint("localhost", 2113);
        EventStoreDBClientSettings settings = EventStoreDBClientSettings.builder()
                .addHost(endpoint)
                .tls(false)
                .defaultCredentials("admin", "changeit")
                .buildConnectionSettings();
        this.client = EventStoreDBClient.create(settings);

        // ObjectMapper mit Java 8 Date/Time Support konfigurieren
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }




    public void storeEvent(String streamName, Object event) throws EventStoreOperationException {
        try {
            EventData eventData = createEventData(event);
            client.appendToStream(streamName, eventData).get();
            LOG.infof("Event stored successfully in stream %s", streamName);
        } catch (Exception e) {
            throw new EventStoreOperationException("Failed to store event", e);
        }
    }

    private EventData createEventData(Object event) throws JsonProcessingException {
        return EventData.builderAsJson(
                        UUID.randomUUID(),
                        event.getClass().getSimpleName(),
                        objectMapper.writeValueAsString(event)
                ).metadataAsJson(createMetadata())
                .build();
    }

    private String createMetadata() {
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "timestamp", Instant.now().toString(),
                    "processedBy", "event-bus-service"
            ));
        } catch (Exception e) {
            LOG.warn("Failed to create metadata", e);
            return "{}";
        }
    }
}