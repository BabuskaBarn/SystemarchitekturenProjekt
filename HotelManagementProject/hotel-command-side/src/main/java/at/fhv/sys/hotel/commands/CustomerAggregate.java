package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

import static io.quarkus.arc.ComponentsProvider.LOG;

@ApplicationScoped
public class CustomerAggregate {

    @Inject
    @RestClient
    EventBusClient eventClient;
//TODO die handle methode und den controller nochmals anschauen ob String.valueOf richtig funktioniert und das der Long im Controller nicht stört
@Transactional
public String handle(CreateCustomerCommand command) {
    try {
        // 1. Event erstellen
        CustomerCreated event = new CustomerCreated(
                command.userId(),
                command.name(),
                command.email(),
                command.address()
        );

        // 2. Event an Event Bus senden
        CustomerCreated response = eventClient.processCustomerCreatedEvent(event);
        LOG.info("Event successfully processed: " + response);

        // 3. Bestätigung zurückgeben
        return command.userId().toString();

    } catch (Exception e) {
        LOG.error("Failed to process customer creation", e);
        throw new RuntimeException("Customer creation failed", e);
    }
}

}
