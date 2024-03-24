package ro.fisa.ssm.exceptions;

import java.io.Serial;

/**
 * Created at 3/25/2024 by Darius
 **/
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3233027363239074300L;

    public NotFoundException(final String message) {
        super(message);
    }
}
