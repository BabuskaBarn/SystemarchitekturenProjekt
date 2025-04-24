package at.fhv.sys.hotel;

import at.fhv.sys.hotel.service.EventReplayService;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
@QuarkusMain(name = "command-app")
public class HotelCommandSideApplication {

    @Inject
    EventReplayService eventReplayService;
    void onStart(@Observes StartupEvent ev) {
        Logger.getLogger(HotelCommandSideApplication.class).info("Starting Hotel Command Side Application");
    }

    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
