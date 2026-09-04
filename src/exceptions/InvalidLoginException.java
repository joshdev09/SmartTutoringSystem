package exceptions;

// Thrown by AuthService.login(...) when the username doesn't exist,
// or when the password doesn't match that username.
public class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}
