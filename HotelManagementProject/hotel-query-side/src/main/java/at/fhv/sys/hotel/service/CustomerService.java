package at.fhv.sys.hotel.service;

import at.fhv.sys.hotel.models.CustomerQueryModel;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @PersistenceContext
    EntityManager entityManager;

    public List<CustomerQueryModel> getAllCustomers() {
        return entityManager.createQuery("SELECT c FROM CustomerQueryModel c", CustomerQueryModel.class).getResultList();
    }

    @Transactional
    public void createCustomer(CustomerQueryModel customer) {
        entityManager.persist(customer);
    }

    @Transactional
    public void updateCustomer(CustomerQueryModel updatedCustomer) {

        CustomerQueryModel existingCustomer = entityManager.find(CustomerQueryModel.class, updatedCustomer.getUserId());

        // Felder aktualisieren
        if (existingCustomer != null) {
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setUserAddress(updatedCustomer.getUserAddress());
        } else {
            throw new IllegalArgumentException("Customer not found with ID: " + updatedCustomer.getUserId());
        }
    }

    public CustomerQueryModel findCustomerById(Long customerId) {
        CustomerQueryModel customer = entityManager.find(CustomerQueryModel.class, customerId);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        return customer;
    }
}
