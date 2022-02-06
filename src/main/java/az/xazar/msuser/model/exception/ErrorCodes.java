package az.xazar.msuser.model.exception;

public final class ErrorCodes {
    public static final String UNEXPECTED_EXCEPTION = "user.unexpected-exception";
    public static final String NOT_FOUND = "user.not-found";
    public static final String NEW_PASSWORD_WRONG = "password.new-wrong";
    public static final String PASSWORD_WRONG = "password.current-wrong";
    public static final String PASSWORD_WRONG_REGEX = "password.regex-wrong";
    public static final String EMAIL_WRONG = "email.regex-wrong";

    private ErrorCodes() {

    }
}
