package exceptions;

public class InvalidKweetException extends Exception {

    public InvalidKweetException() { }

    public InvalidKweetException(String message) {
        super(message);
    }
}
