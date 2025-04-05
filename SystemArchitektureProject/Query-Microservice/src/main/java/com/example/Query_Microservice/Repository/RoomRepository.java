package com.example.Query_Microservice.Repository;



import com.example.Query_Microservice.Domain.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Rooms, UUID> {
}
