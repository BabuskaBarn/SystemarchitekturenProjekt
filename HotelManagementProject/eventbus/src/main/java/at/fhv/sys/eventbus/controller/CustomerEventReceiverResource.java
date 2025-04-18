package at.fhv.sys.eventbus.controller;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.eventbus.model.CustomerView;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

@Path("/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerEventReceiverResource {

    private static final Logger logger = Logger.getLogger(CustomerEventReceiverResource.class.getName());

    @POST
    @Path("/customer-created")
    @Transactional
    public Response receiveCustomerCreatedEvent(CustomerCreated event) {
        logger.info("Saving CustomerCreatedEvent: " + event.getUserId());

        CustomerView view = new CustomerView();
        view.customerId = event.getUserId();
        view.email = event.getEmail();
        view.address = event.getAddress();

        view.persist();  // Panache magic

        return Response.ok().build();
    }
}
