package ro.fisa.ssm.exceptions;

import java.io.Serial;

/**
 * Created at 3/17/2024 by Darius
 **/
public class EmployeeExtractionException extends AppRuntimeException{
    @Serial
    private static final long serialVersionUID = 6672048978535881184L;

    public EmployeeExtractionException(Exception e) {
        super("Cannot extract employee from registry");
    }
}
