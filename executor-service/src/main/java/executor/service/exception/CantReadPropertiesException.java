package executor.service.exception;

public class CantReadPropertiesException extends RuntimeException{
    public CantReadPropertiesException(String message) {
        super("cant read properties. reason: " + message);
    }
}
