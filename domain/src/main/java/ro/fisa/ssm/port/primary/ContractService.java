package ro.fisa.ssm.port.primary;

import org.springframework.data.domain.Page;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.model.Contract;

import java.util.Collection;

/**
 * Created at 3/24/2024 by Darius
 **/
public interface ContractService {

    Page<Contract> getContractPage(int number, int size, ContractContext context);

    Collection<Contract> getByEmployeeCnp(final String cnp,
                                          final ContractContext context
    );

    Contract getByNumber(final String number,
                         final ContractContext context
    );
}
