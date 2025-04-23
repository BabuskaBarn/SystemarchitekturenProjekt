package at.fhv.sys.hotel.query.controller;

import at.fhv.sys.hotel.commands.shared.events.CustomerCreated;
import at.fhv.sys.hotel.models.CustomerQueryModel;
import at.fhv.sys.hotel.projection.CustomerProjection;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logmanager.Logger;

import java.util.List;

import static io.quarkus.arc.ComponentsProvider.LOG;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerQueryController {

    @Inject
    CustomerProjection customerProjection;
    @Inject
    EntityManager entityManager;

    public CustomerQueryController() {
    }

    @POST
    @Path("/customerCreated")
    public Response customerCreated(CustomerCreated event) {
        Logger.getAnonymousLogger().info("Received event: " + event);
        customerProjection.processCustomerCreatedEvent(event);
        return Response.ok(event).build();
    }

    @GET
    @Path("/customers")
    public Response getCustomers(@QueryParam("name") String name) {
        try {
            List<CustomerQueryModel> customers;

            if (name != null && !name.isEmpty()) {
                customers = entityManager.createQuery(
                                "SELECT c FROM CustomerQueryModel c WHERE LOWER(c.name) LIKE LOWER(:name)",
                                CustomerQueryModel.class)
                        .setParameter("name", "%" + name + "%")
                        .getResultList();
            } else {
                customers = entityManager.createQuery(
                                "SELECT c FROM CustomerQueryModel c",
                                CustomerQueryModel.class)
                        .getResultList();
            }

            return Response.ok(customers).build();
        } catch (Exception e) {
            LOG.error("Error fetching customers", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching customers")
                    .build();
        }
    }
}