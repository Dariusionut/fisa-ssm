package ro.fisa.ssm.exceptions;

/**
 * Created at 3/9/2024 by Darius
 **/
public class AppAuthenticationException extends AppRuntimeException {
    public AppAuthenticationException() {
        super("Invalid username or password");
    }
}
