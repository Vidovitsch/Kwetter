package exceptions;

public class AlreadyLikedException extends Exception {
    public AlreadyLikedException() { }

    public AlreadyLikedException(String message) {
        super(message);
    }

}
