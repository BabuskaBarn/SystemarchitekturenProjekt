package at.fhv.sys.hotel.projection;


import at.fhv.sys.hotel.commands.shared.events.RoomCreated;
import at.fhv.sys.hotel.models.RoomQueryModel;
import at.fhv.sys.hotel.models.RoomQueryPanacheModel;
import at.fhv.sys.hotel.service.RoomService;
import at.fhv.sys.hotel.service.RoomServicePanache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.logging.Logger;

@ApplicationScoped
public class RoomProjection {

    @Inject
    RoomService roomService;

    @Inject
    RoomServicePanache roomServicePanache;

    public void processRoomCreatedEvent(RoomCreated roomCreatedEvent){
        Logger.getAnonymousLogger().info("Processing event: " + roomCreatedEvent);
        roomService.createRoom(new RoomQueryModel(roomCreatedEvent.getRoomId(), roomCreatedEvent.getRoomCapacity(), roomCreatedEvent.getRoomNumber()));

        RoomQueryPanacheModel room= new RoomQueryPanacheModel();
        room.roomId=roomCreatedEvent.getRoomId();
        room.roomCapacity=roomCreatedEvent.getRoomCapacity();
        room.roomNumber=roomCreatedEvent.getRoomNumber();
        roomServicePanache.createRoom(room);



    }
}
