package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.CreateCustomerCommand;
import at.fhv.sys.hotel.commands.CustomerAggregate;
import at.fhv.sys.hotel.commands.UpdateCustomerCommand;
import at.fhv.sys.hotel.commands.shared.events.CustomerUpdated;
import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.EventStoreDBClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.Iterator;
import java.util.UUID;

@Path("/api/commands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerCommandController {

    CustomerAggregate customerAggregate;
    private EventStoreDBClient EventStore;

    public CustomerCommandController(CustomerAggregate customerAggregate) {
        this.customerAggregate = customerAggregate;
    }
//TODO @QueryParam("customerId") Long customerId, eventuell auf String setzen
    @POST
    @Path("/createCustomer")
    public Response createCustomer(
            @QueryParam("name") String name,
            @QueryParam("email") String email,
            @QueryParam("address") String address) {

        // Automatische ID-Generierung
        Long customerId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        CreateCustomerCommand command = new CreateCustomerCommand(
                customerId,
                name,
                email,
                address
        );

        String result = customerAggregate.handle(command);

        return Response.ok()
                .entity("Customer created with ID: " + customerId)
                .build();
    }

    @POST
    @Path("/{customerId}/update")
    public String updateCustomer(@PathParam("customerId") Long customerId, @QueryParam("email") String email, @QueryParam("address") String address) {

        // 1. Command erstellen
        UpdateCustomerCommand command = new UpdateCustomerCommand(customerId, email, address);
        // 2. Event erzeugen (über Aggregate)
        CustomerUpdated event = CustomerAggregate.updateCustomer(command);

        EventStore.appendToStream("Customer-" + customerId, (Iterator<EventData>) event);

        return "Customer updated";
    }

    @POST
    @Path("/{customerId}/delete")
    public String deleteCustomer(@PathParam("customerId") Long customerId) {
        // TBD: delete customer
        return "Customer deleted";
    }
}