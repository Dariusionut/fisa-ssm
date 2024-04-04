package ro.fisa.ssm.port.secondary;

import org.springframework.data.domain.Pageable;
import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Optional;

/**
 * Created at 3/27/2024 by Darius
 **/

public interface EmployerRepository {

    DomainPage<Employer> fetchPage(Pageable pageable);

    Optional<Employer> fetchByName(final String name);

    Employer save(Employer employer);
}
