package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.exceptions.ContractNotFoundException;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.port.primary.ContractService;
import ro.fisa.ssm.port.secondary.ContractRepository;

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
    public Page<Contract> getContractPage(int number, int size, ContractContext context) {
        return this.contractRepository.fetchContractPage(PageRequest.of(number, size), context);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Contract> getByEmployeeCnp(final String cnp,
                                                 ContractContext context
    ) {
        return this.contractRepository.fetchByEmployeeCnp(cnp, context);
    }

    @Override
    @Transactional(readOnly = true)
    public Contract getByNumber(String number, ContractContext context) {
        return this.contractRepository.fetchByNumber(number, context)
                .orElseThrow(ContractNotFoundException::new);
    }
}
