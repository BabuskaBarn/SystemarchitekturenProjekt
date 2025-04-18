package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

@ApplicationScoped
public class CustomerAggregate {

    private static final Logger logger = Logger.getLogger(CustomerAggregate.class.getName());

    @Inject
    @RestClient
    EventBusClient eventClient;

    public String handle(CreateCustomerCommand command) {
        CustomerCreated event = new CustomerCreated(
                command.userId(),
                command.email(),
                command.address()
        );

        var response = eventClient.processCustomerCreatedEvent(event);
        logger.info("Sent CustomerCreatedEvent: " + response.toString());

        return "Customer created with ID: " + command.userId();
    }
}
