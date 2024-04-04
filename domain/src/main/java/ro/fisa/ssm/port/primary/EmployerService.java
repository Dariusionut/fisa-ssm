package ro.fisa.ssm.port.primary;

import ro.fisa.ssm.model.Employer;
import ro.fisa.ssm.structures.DomainPage;

/**
 * Created at 4/4/2024 by Darius
 **/
public interface EmployerService {

    DomainPage<Employer> getEmployers(int number, int size);
}
