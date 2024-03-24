package ro.fisa.ssm.exceptions;

import java.io.Serial;

/**
 * Created at 3/25/2024 by Darius
 **/
public class ContractNotFoundException extends NotFoundException {
    @Serial
    private static final long serialVersionUID = -769831568541420081L;

    public ContractNotFoundException() {
        super("Contract not found");
    }
}
