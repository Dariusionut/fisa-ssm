package ro.fisa.ssm.exceptions;

import java.io.Serial;

/**
 * Created at 3/9/2024 by Darius
 **/
public class AppRuntimeException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -8718831530846192466L;


    public AppRuntimeException(final String message){
        super(message);
    }
}
