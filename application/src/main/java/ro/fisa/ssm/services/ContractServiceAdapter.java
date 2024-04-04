package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.exceptions.ContractNotFoundException;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.port.primary.ContractService;
import ro.fisa.ssm.port.secondary.ContractRepository;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

/**
 * Created at 3/24/2024 by Darius
 **/

@Service
@RequiredArgsConstructor
public class ContractServiceAdapter implements ContractService {

    private final ContractRepository contractRepository;

    @Override
    @Transactional(readOnly = true)
    public DomainPage<Contract> getContractPage(int number, int size, String employerName, ContractContext context) {
        return this.contractRepository.fetchContractPage(PageRequest.of(number, size), employerName, context);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Contract> getByEmployeeCnp(final String cnp,
                                                 ContractContext context
    ) {
        final var contracts = this.contractRepository.fetchByEmployeeCnp(cnp, context);
        if (contracts.isEmpty()) {
            throw new ContractNotFoundException();
        }
        return contracts;
    }

    @Override
    @Transactional(readOnly = true)
    public Contract getByNumber(String number, ContractContext context) {
        return this.contractRepository.fetchByNumber(number, context)
                .orElseThrow(ContractNotFoundException::new);
    }
}
