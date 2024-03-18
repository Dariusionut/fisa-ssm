package ro.fisa.ssm.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ro.fisa.ssm.model.Job;
import ro.fisa.ssm.persistence.job.entity.JpaJobRepository;
import ro.fisa.ssm.persistence.job.mapper.JobEntityMapper;
import ro.fisa.ssm.port.secondary.JobRepository;

import java.util.Optional;

/**
 * Created at 3/17/2024 by Darius
 **/
@Repository
@RequiredArgsConstructor
public class JobRepositoryAdapter implements JobRepository {

    private final JpaJobRepository jpaJobRepository;

    @Override
    public Optional<Job> fetchByName(String jobName) {
        return this.jpaJobRepository.fetchByName(jobName)
                .map(JobEntityMapper.INSTANCE::toModel);
    }
}
