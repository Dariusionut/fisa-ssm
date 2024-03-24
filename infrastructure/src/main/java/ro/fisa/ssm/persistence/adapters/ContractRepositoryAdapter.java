package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;
import ro.fisa.ssm.persistence.contract.entity.JpaContractRepository;
import ro.fisa.ssm.persistence.contract.mapper.ContractEntityMapper;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.persistence.employer.entity.JpaEmployerRepository;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.port.secondary.ContractRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created at 3/24/2024 by Darius
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractRepositoryAdapter implements ContractRepository {

    private final JpaContractRepository jpaContractRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaEmployerRepository jpaEmployerRepository;

    private static Function<ContractEntity, Contract> mapWithContext(ContractContext context) {
        return c -> ContractEntityMapper.INSTANCE.toModel(c, context);
    }

    @Override
    public Optional<Contract> fetchByNumber(String number) {
        return this.jpaContractRepository.findByNumber(number)
                .map(ContractEntityMapper.INSTANCE::toModel);
    }

    @Override
    public Optional<Contract> fetchByNumber(String number, ContractContext context) {
        return this.jpaContractRepository.findByNumber(number)
                .map(mapWithContext(context));
    }

    @Override
    public Collection<Contract> fetchByEmployeeCnp(String cnp,
                                                   ContractContext context) {
        return this.jpaContractRepository.fetchByEmployeeCnp(cnp)
                .parallel()
                .map(mapWithContext(context))
                .collect(Collectors.toSet());
    }

    @Override
    public Page<Contract> fetchContractPage(Pageable pageable, ContractContext context) {
        return this.jpaContractRepository.fetchContractPage(pageable)
                .map(mapWithContext(context));
    }

    @Override
    public Contract save(Contract contract) {
        final ContractEntity contractEntity = ContractEntityMapper.INSTANCE.toEntity(contract);
        final UserEntity employee = contractEntity.getEmployee();
        final EmployerEntity employer = contractEntity.getEmployer();

        if (employee.getId() == null) {
            this.jpaUserRepository.save(employee);
        }

        if (employer.getId() == null) {
            this.jpaEmployerRepository.save(employer);
        }

        return null;
    }

    @Override
    public Collection<Contract> saveAll(Collection<Contract> contracts) {
        return null;
    }
}
