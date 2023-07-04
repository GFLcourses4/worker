package executor.service.exception;

public class JsonReaderException extends RuntimeException {
    public JsonReaderException(Throwable cause) {
        super("Can`t read Json file", cause);
    }
}
