package util;

public class BooleanResult{

    private Object result;
    private boolean succes;

    public BooleanResult() {
    }

    public BooleanResult(Object result, boolean succes) {
        this.result = result;
        this.succes = succes;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }
}
