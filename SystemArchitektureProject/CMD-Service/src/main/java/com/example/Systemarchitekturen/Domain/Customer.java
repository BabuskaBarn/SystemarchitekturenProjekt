package com.example.Systemarchitekturen.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class Customer {
    @Id
    private UUID customerId;


    private String name;

    public Customer() {
    };

    @OneToMany(mappedBy = "customer")
    private List<Bookings> bookings;


    public Customer(String name) {
        this.name = name;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }
}
