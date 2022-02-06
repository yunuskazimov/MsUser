package az.xazar.msuser.model.exception;

public class PasswordWrongFoundException extends RuntimeException {
    public PasswordWrongFoundException(String message) {
        super(message);
    }
}
