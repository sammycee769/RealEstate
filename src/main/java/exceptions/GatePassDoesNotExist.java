package exceptions;

public class GatePassDoesNotExist extends RuntimeException {
    public GatePassDoesNotExist(String message) {
        super(message);
    }
}
