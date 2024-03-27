package ro.fisa.ssm.port.secondary;

import ro.fisa.ssm.model.Employer;

import java.util.Optional;

/**
 * Created at 3/27/2024 by Darius
 **/

public interface EmployerRepository {

    Optional<Employer> fetchByName(final String name);

    Employer save(Employer employer);
}
