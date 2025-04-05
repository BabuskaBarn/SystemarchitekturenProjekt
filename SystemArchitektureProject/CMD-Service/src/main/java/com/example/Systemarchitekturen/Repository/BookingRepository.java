package com.example.Systemarchitekturen.Repository;

import com.example.Systemarchitekturen.Domain.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BookingRepository extends JpaRepository<UUID, Bookings> {
}
