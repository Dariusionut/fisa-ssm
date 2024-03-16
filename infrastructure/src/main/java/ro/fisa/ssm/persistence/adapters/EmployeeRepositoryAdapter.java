package ro.fisa.ssm.persistence.adapters;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.persistence.contract.entity.ContractEntity;
import ro.fisa.ssm.persistence.contract.entity.JpaContractRepository;
import ro.fisa.ssm.persistence.job.entity.JobEntity;
import ro.fisa.ssm.persistence.job.entity.JpaJobRepository;
import ro.fisa.ssm.persistence.nationality.JpaNationalityRepository;
import ro.fisa.ssm.persistence.nationality.entity.NationalityEntity;
import ro.fisa.ssm.persistence.user.JpaUserRepository;
import ro.fisa.ssm.persistence.user.entity.UserEntity;
import ro.fisa.ssm.persistence.user.mapper.UserEntityMapper;
import ro.fisa.ssm.port.secondary.EmployeeRepository;

import java.util.Collection;

/**
 * Created at 3/10/2024 by Darius
 **/

@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepository {
    private final JpaUserRepository jpaUserRepository;
    private final JpaNationalityRepository jpaNationalityRepository;
    private final JpaContractRepository jpaContractRepository;
    private final JpaJobRepository jpaJobRepository;

    public Employee save(Employee employee) {
        final UserEntity userEntity = UserEntityMapper.INSTANCE.toEntity(employee);
        this.checkNationality(userEntity);
        this.manageContract(userEntity);
        final Collection<ContractEntity> contracts = userEntity.getContracts();

        if (contracts != null && !contracts.isEmpty()) {
            userEntity.setContracts(this.jpaContractRepository.saveAll(contracts));
        }

        final UserEntity saved = this.jpaUserRepository.save(userEntity);
        return UserEntityMapper.INSTANCE.toEmployeeModel(saved);
    }

    private void manageContract(UserEntity userEntity) {
        userEntity.getContracts().forEach(contract -> {
            final JobEntity jobEntity = contract.getJob();


            if (jobEntity != null && jobEntity.getName() != null) {
                if (jobEntity.getName() == null){
                    System.out.println(jobEntity);
                }
                final JobEntity persisted = this.jpaJobRepository.fetchByName(jobEntity.getName())
                        .orElseGet(() -> this.jpaJobRepository.save(jobEntity));
                contract.setJob(persisted);
            }
        });

    }


    private void checkNationality(UserEntity userEntity) {
        final NationalityEntity nationality = userEntity.getNationality();
        if ((nationality == null) || StringUtils.isBlank(nationality.getName())) {
            return;
        }

        final NationalityEntity saved = this.jpaNationalityRepository.fetchByName(nationality.getName())
                .orElseGet(() -> this.jpaNationalityRepository.save(nationality));

        userEntity.setNationality(saved);


    }
}
