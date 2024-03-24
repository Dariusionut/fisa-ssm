package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;
import ro.fisa.ssm.persistence.contract.entity.JpaContractRepository;
import ro.fisa.ssm.persistence.contract.mapper.ContractEntityMapper;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.persistence.employer.entity.JpaEmployerRepository;
import ro.fisa.ssm.persistence.job.entity.JobEntity;
import ro.fisa.ssm.persistence.job.entity.JpaJobRepository;
import ro.fisa.ssm.persistence.nationality.JpaNationalityRepository;
import ro.fisa.ssm.persistence.nationality.entity.NationalityEntity;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.persistence.user.mapper.UserEntityMapper;
import ro.fisa.ssm.port.secondary.EmployeeRepository;

import java.util.Collection;
import java.util.Optional;

import static ro.fisa.ssm.utils.AppObjectUtils.isObjectOrPropertyBlank;

/**
 * Created at 3/10/2024 by Darius
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepository {
    private final JpaNationalityRepository jpaNationalityRepository;
    private final JpaContractRepository jpaContractRepository;
    private final JpaJobRepository jpaJobRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaEmployerRepository jpaEmployerRepository;


    @Override
    public Optional<Employee> findByCnp(String cnp) {
        return this.jpaUserRepository.findByCnp(cnp)
                .map(UserEntityMapper.INSTANCE::toEmployeeModel);
    }

    @Override
    public Collection<Contract> saveAll(Collection<Contract> contracts) {
        return contracts.stream().map(this::save).toList();
    }

    public Contract save(Contract employeeContractModel) {
        ContractEntity contract = ContractEntityMapper.INSTANCE.toEntity(employeeContractModel);
        final UserEntity userEntity = contract.getEmployee();
        this.checkNationality(userEntity);
        contract.setJob(this.checkJob(contract.getJob()));
        if (contract.getEmployee().getId() == null) {
            this.jpaUserRepository.findByCnp(userEntity.getCnp())
                    .map(UserEntity::getId)
                    .ifPresentOrElse(userEntity::setId, () -> this.jpaUserRepository.save(userEntity));
        }
        final EmployerEntity employerEntity = contract.getEmployer();
        if (employerEntity != null) {
            this.jpaEmployerRepository.fetchByName(employerEntity.getName())
                    .ifPresentOrElse(contract::setEmployer, () -> {
                        final EmployerEntity savedEmployer = this.jpaEmployerRepository.save(employerEntity);
                        contract.setEmployer(savedEmployer);
                    });
        }
        final ContractEntity saved = this.jpaContractRepository.save(contract);
        return ContractEntityMapper.INSTANCE.toModel(saved);
    }

    private JobEntity checkJob(JobEntity jobEntity) {
        return this.jpaJobRepository.fetchByName(jobEntity.getName()).orElseGet(() -> this.jpaJobRepository.save(jobEntity));
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
