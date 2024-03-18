package ro.fisa.ssm.port.secondary;

import ro.fisa.ssm.model.Job;

import java.util.Optional;

/**
 * Created at 3/17/2024 by Darius
 **/
public interface JobRepository {

    Optional<Job> fetchByName(final String jobName);
}
