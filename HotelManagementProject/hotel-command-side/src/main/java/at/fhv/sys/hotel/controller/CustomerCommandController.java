package at.fhv.sys.hotel.controller;

import at.fhv.sys.hotel.commands.CreateCustomerCommand;
import at.fhv.sys.hotel.commands.CustomerAggregate;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerCommandController {

    CustomerAggregate customerAggregate;

    public CustomerCommandController(CustomerAggregate customerAggregate) {
        this.customerAggregate = customerAggregate;
    }

//TODO @QueryParam("customerId") Long customerId, eventuell auf String setzen
    @POST
    @Path("/createCustomer")
    public String createCustomer(@QueryParam("customerId") Long customerId, @QueryParam("name") String name, @QueryParam("email") String email, @QueryParam("address") String address) {
        return customerAggregate.handle(new CreateCustomerCommand(customerId, name, email, address));

    }

    @POST
    @Path("/{customerId}/update")
    public String updateCustomer(@PathParam("customerId") Long customerId, @QueryParam("email") String email) {
        // TBD: process customer
        return "Customer updated";
    }

    @POST
    @Path("/{customerId}/delete")
    public String deleteCustomer(@PathParam("customerId") Long customerId) {
        // TBD: delete customer
        return "Customer deleted";
    }
}