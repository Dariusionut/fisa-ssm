package ro.fisa.ssm.port.secondary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.model.Contract;

import java.util.Collection;
import java.util.Optional;

/**
 * Created at 3/24/2024 by Darius
 **/
public interface ContractRepository {
    Optional<Contract> fetchByNumber(final String number);
    Optional<Contract> fetchByNumber(final String number, ContractContext context);

    Page<Contract> fetchContractPage(final Pageable pageable, ContractContext context);

    Collection<Contract> fetchByEmployeeCnp(final String cnp,
                                            ContractContext context);

    Contract save(final Contract contract);

    Collection<Contract> saveAll(final Collection<Contract> contracts);
}
