package at.fhv.sys.hotel.projection;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.commands.shared.events.CustomerUpdated;
import at.fhv.sys.hotel.models.CustomerQueryModel;
import at.fhv.sys.hotel.models.CustomerQueryPanacheModel;
import at.fhv.sys.hotel.service.CustomerService;
import at.fhv.sys.hotel.service.CustomerServicePanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.logging.Logger;

@ApplicationScoped
public class CustomerProjection {

    @Inject
    CustomerService customerService;

    @Inject
    CustomerServicePanache customerServicePanache;


    public CustomerProjection() {}

    public void processCustomerCreatedEvent(CustomerCreated customerCreatedEvent) {
        Logger.getAnonymousLogger().info("Processing event: " + customerCreatedEvent);
        customerService.createCustomer(new CustomerQueryModel(customerCreatedEvent.getUserId(), customerCreatedEvent.getName(),customerCreatedEvent.getEmail(), customerCreatedEvent.getAddress()));

        CustomerQueryPanacheModel customer = new CustomerQueryPanacheModel();
        customer.userId = customerCreatedEvent.getUserId();
        customer.name= customerCreatedEvent.getName();
        customer.email = customerCreatedEvent.getEmail();
        customer.address = customerCreatedEvent.getAddress();
        customerServicePanache.createCustomer(customer);

    }

    public void on(CustomerUpdated event) {
        CustomerQueryModel updatedCustomer = new CustomerQueryModel();
        updatedCustomer.setEmail(event.getEmail());
        updatedCustomer.setUserAddress(event.getAddress());

        customerService.updateCustomer(updatedCustomer);
    }
}
