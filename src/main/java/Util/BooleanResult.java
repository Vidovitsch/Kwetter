package Util;

public class BooleanResult {

    private String message;
    private boolean succes;

    public BooleanResult(String message, boolean succes) {
        this.message = message;
        this.succes = succes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }
}
