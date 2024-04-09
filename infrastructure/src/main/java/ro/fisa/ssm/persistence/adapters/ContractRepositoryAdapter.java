package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.context.ContractContext;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;
import ro.fisa.ssm.persistence.contract.entity.JpaContractRepository;
import ro.fisa.ssm.persistence.contract.mapper.ContractEntityMapper;
import ro.fisa.ssm.persistence.job.JpaJobRepository;
import ro.fisa.ssm.persistence.job.entity.JobEntity;
import ro.fisa.ssm.persistence.nationality.JpaNationalityRepository;
import ro.fisa.ssm.persistence.nationality.entity.NationalityEntity;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.port.secondary.ContractRepository;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ro.fisa.ssm.utils.AppObjectUtils.isObjectOrPropertyBlank;

/**
 * Created at 3/24/2024 by Darius
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractRepositoryAdapter implements ContractRepository {

    private final JpaContractRepository jpaContractRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaJobRepository jpaJobRepository;
    private final JpaNationalityRepository jpaNationalityRepository;


    private static Function<ContractEntity, Contract> mapWithContext(ContractContext context) {
        return c -> ContractEntityMapper.INSTANCE.toModel(c, context);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Contract> fetchAllByNumber(Collection<String> numbers) {
        return this.jpaContractRepository.fetchAllByNumbers(numbers)
                .parallel().map(ContractEntityMapper.INSTANCE::toModel)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
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
    public DomainPage<Contract> fetchContractPage(Pageable pageable, String employerName, ContractContext context) {
        final var contractPage = this.jpaContractRepository.fetchContractPage(employerName, pageable);
        return ContractEntityMapper.INSTANCE.toDomainPage(contractPage, context);
    }


    @Override
    public Collection<Contract> saveAll(Collection<Contract> contracts) {
        final Map<String, UserEntity> employees = new HashMap<>();
        final Map<String, JobEntity> jobs = new HashMap<>();
        final Collection<ContractEntity> contractEntities = contracts.parallelStream().map(c -> {
            ContractEntity contract = ContractEntityMapper.INSTANCE.toEntity(c);
            final UserEntity employee = contract.getEmployee();
            final JobEntity jobEntity = contract.getJob();
            this.checkNationality(employee);
            if (contract.getEmployee().getId() == null) {
                this.jpaUserRepository.findIdByCnp(employee.getCnp())
                        .ifPresentOrElse(employee::setId, () -> employees.put(employee.getCnp().trim(), employee));
            }
            if (jobEntity.getId() == null) {
                this.jpaJobRepository.fetchIdByName(jobEntity.getName())
                        .ifPresentOrElse(jobEntity::setId, () -> jobs.put(jobEntity.getName().trim(), jobEntity));
            }
            return contract;
        }).toList();

        log.info("Saving employees..");
        if (!employees.isEmpty()) {
            Collection<UserEntity> savedEmployees = this.jpaUserRepository.saveAll(employees.values());
            contractEntities.parallelStream().forEach(contractToSave -> savedEmployees.parallelStream().forEach(savedEmployee -> {
                final UserEntity contractEmp = contractToSave.getEmployee();
                if (savedEmployee.getCnp().trim().equalsIgnoreCase(contractEmp.getCnp())) {
                    contractToSave.setEmployee(savedEmployee);
                }
            }));
        }
        log.info("Saving jobs..");

        if (!jobs.isEmpty()) {
            final Collection<JobEntity> savedJobs = this.jpaJobRepository.saveAll(jobs.values());
            contractEntities.parallelStream().forEach(contractToSave -> savedJobs.parallelStream().forEach(savedJob -> {
                final JobEntity contractJob = contractToSave.getJob();
                if (savedJob.getName().equalsIgnoreCase(contractJob.getName())) {
                    contractToSave.setJob(savedJob);
                }
            }));
        }
        final Collection<ContractEntity> savedContracts = this.jpaContractRepository.saveAll(contractEntities);
        return savedContracts.parallelStream().map(ContractEntityMapper.INSTANCE::toModel).toList();
    }

    private void checkNationality(UserEntity userEntity) {
        final NationalityEntity nationality = userEntity.getNationality();
        if (isObjectOrPropertyBlank(nationality, NationalityEntity::getName)) {
            return;
        }
        final NationalityEntity saved = this.jpaNationalityRepository.fetchByName(nationality.getName())
                .orElseGet(() -> this.jpaNationalityRepository.save(nationality));

        userEntity.setNationality(saved);

    }
}
