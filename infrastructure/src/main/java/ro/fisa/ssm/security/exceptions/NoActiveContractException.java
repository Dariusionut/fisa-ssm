package ro.fisa.ssm.security.exceptions;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**
 * Created at 4/9/2024 by Darius
 **/
public class NoActiveContractException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = 3599595135993615929L;

    public NoActiveContractException() {
        super("No active contract");
    }
}
