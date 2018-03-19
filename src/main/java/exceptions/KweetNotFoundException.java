package exceptions;

public class KweetNotFoundException extends Exception {

    public KweetNotFoundException() { }

    public KweetNotFoundException(String message) {
        super(message);
    }
}
