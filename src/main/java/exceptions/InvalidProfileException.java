package exceptions;

public class InvalidProfileException extends Exception {

    public InvalidProfileException() { }

    public InvalidProfileException(String message) {
        super(message);
    }
}
