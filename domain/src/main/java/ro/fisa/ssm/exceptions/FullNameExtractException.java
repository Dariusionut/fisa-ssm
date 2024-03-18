package ro.fisa.ssm.exceptions;

import java.io.Serial;

/**
 * Created at 3/17/2024 by Darius
 **/
public class FullNameExtractException extends AppCompileException {
    @Serial
    private static final long serialVersionUID = -2381272855898119651L;

    public FullNameExtractException(String message) {
        super(message);
    }
}
