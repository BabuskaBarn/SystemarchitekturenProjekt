package at.fhv.sys.hotel.service;

import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.EventStoreDBClientSettings;
import com.eventstore.dbclient.EventStoreDBConnectionString;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.ExecutionException;


import java.util.UUID;

@ApplicationScoped
public class EventStoreService {

    private EventStoreDBClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    void init() {
        EventStoreDBClientSettings settings = EventStoreDBConnectionString.parseOrThrow(
                "esdb://localhost:2113?tls=false" // oder aus application.properties laden
        );
        client = EventStoreDBClient.create(settings);
    }

    public void appendEvent(String streamName, Object event) {
        try {
            String eventType = event.getClass().getSimpleName();
            String json = objectMapper.writeValueAsString(event);

            EventData eventData = EventData
                    .builderAsJson(UUID.randomUUID(), eventType, json)
                    .build();

            client.appendToStream(streamName, eventData).get();
            System.out.println("Event gespeichert: " + eventType);
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern des Events: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
