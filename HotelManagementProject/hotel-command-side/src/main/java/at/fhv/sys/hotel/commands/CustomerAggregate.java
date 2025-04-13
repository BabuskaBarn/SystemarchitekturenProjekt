package at.fhv.sys.hotel.commands;

import at.fhv.sys.hotel.client.EventBusClient;
import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.logging.Logger;

@ApplicationScoped
public class CustomerAggregate {

    @Inject
    @RestClient
    EventBusClient eventClient;
//TODO die handle methode und den controller nochmals anschauen ob String.valueOf richtig funktioniert und das der Long im Controller nicht stört
    public String handle(CreateCustomerCommand command) {
        CustomerCreated event= new CustomerCreated(command.userId(), command.email(),command.address());
        //CustomerCreated event = new CustomerCreated(command.userId(), command.email());

        Logger.getAnonymousLogger().info(eventClient.processCustomerCreatedEvent(event).toString());
        //wir geben dem command einen Long dieser erwartet aber einen String;
        return String.valueOf(command.userId());
    }

}
