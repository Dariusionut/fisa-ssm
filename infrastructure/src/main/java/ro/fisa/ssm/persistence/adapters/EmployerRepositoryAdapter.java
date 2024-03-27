package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.persistence.employer.entity.JpaEmployerRepository;
import ro.fisa.ssm.persistence.employer.mapper.EmployerEntityMapper;
import ro.fisa.ssm.port.secondary.EmployerRepository;

import java.util.Optional;

/**
 * Created at 3/27/2024 by Darius
 **/
@Component
@RequiredArgsConstructor
public class EmployerRepositoryAdapter implements EmployerRepository {

    private final JpaEmployerRepository jpaEmployerRepository;

    @Override
    public Optional<Employer> fetchByName(String name) {
        return this.jpaEmployerRepository.fetchByName(name)
                .map(EmployerEntityMapper.INSTANCE::toModel);
    }

    @Override
    public Employer save(Employer employer) {
        EmployerEntity employerEntity = EmployerEntityMapper.INSTANCE.toEntity(employer);
        employerEntity = this.jpaEmployerRepository.save(employerEntity);
        return EmployerEntityMapper.INSTANCE.toModel(employerEntity);


    }
}
