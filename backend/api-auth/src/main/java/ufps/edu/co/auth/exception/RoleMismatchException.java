package ufps.edu.co.auth.exception;

public class RoleMismatchException extends AuthDomainException {

    public RoleMismatchException() {
        super("User role does not match the requested role for login");
    }

    public RoleMismatchException(String message) {
        super(message);
    }
}
