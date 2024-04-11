package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.persistence.employer.JpaEmployerRepository;
import ro.fisa.ssm.persistence.employer.entity.EmployerEntity;
import ro.fisa.ssm.persistence.employer.mapper.EmployerDetailsProjectionMapper;
import ro.fisa.ssm.persistence.employer.mapper.EmployerEntityMapper;
import ro.fisa.ssm.persistence.employer.projection.EmployerDetailsProjection;
import ro.fisa.ssm.persistence.induction.JpaInductionRepository;
import ro.fisa.ssm.persistence.induction.entity.InductionEntity;
import ro.fisa.ssm.port.secondary.EmployerRepository;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Optional;

/**
 * Created at 3/27/2024 by Darius
 **/
@Component
@RequiredArgsConstructor
public class EmployerRepositoryAdapter implements EmployerRepository {

    private final JpaEmployerRepository jpaEmployerRepository;
    private final JpaInductionRepository jpaInductionRepository;

    @Override
    @Transactional(readOnly = true)
    public DomainPage<Employer> fetchPage(Pageable pageable) {
        final Page<EmployerDetailsProjection> entityPage = this.jpaEmployerRepository.fetchPage(pageable);
        return EmployerDetailsProjectionMapper.INSTANCE.toDomainPage(entityPage);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employer> fetchByName(String name) {
        return this.jpaEmployerRepository.fetchByName(name)
                .map(EmployerDetailsProjectionMapper.INSTANCE::toModel);
    }

    @Override
    public Employer save(Employer employer) {
        EmployerEntity employerEntity = EmployerEntityMapper.INSTANCE.toEntity(employer);
        final InductionEntity induction = employerEntity.getInduction();
        if (induction != null) {
            final InductionEntity savedInduction = this.jpaInductionRepository.save(induction);
            employerEntity.setInduction(savedInduction);
        }
        employerEntity = this.jpaEmployerRepository.save(employerEntity);
        return EmployerEntityMapper.INSTANCE.toModel(employerEntity);


    }
}
