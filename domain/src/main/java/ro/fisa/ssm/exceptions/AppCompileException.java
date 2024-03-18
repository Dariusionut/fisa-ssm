package ro.fisa.ssm.exceptions;

import java.io.Serial;

/**
 * Created at 3/17/2024 by Darius
 **/
public class AppCompileException extends Exception {
    @Serial
    private static final long serialVersionUID = -6603650324935452370L;

    public AppCompileException(final String message) {
        super(message);
    }


}
