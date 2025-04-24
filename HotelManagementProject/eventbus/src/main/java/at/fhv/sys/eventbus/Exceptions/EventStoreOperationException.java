package at.fhv.sys.eventbus.Exceptions;

public class EventStoreOperationException extends RuntimeException {
    public EventStoreOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}