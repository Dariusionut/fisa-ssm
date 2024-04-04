package ro.fisa.ssm.port.primary;

import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

/**
 * Created at 3/24/2024 by Darius
 **/
public interface ContractService {

    DomainPage<Contract> getContractPage(int number, int size, String employerName, ContractContext context);

    Collection<Contract> getByEmployeeCnp(final String cnp,
                                          final ContractContext context
    );

    Contract getByNumber(final String number,
                         final ContractContext context
    );
}
