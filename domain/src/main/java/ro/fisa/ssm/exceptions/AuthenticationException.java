package ro.fisa.ssm.exceptions;

/**
 * Created at 3/9/2024 by Darius
 **/
public class AuthenticationException extends AppRuntimeException {
    public AuthenticationException() {
        super("Invalid username or password");
    }
}
