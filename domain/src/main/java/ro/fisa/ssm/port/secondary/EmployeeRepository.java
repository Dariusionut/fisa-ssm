package ro.fisa.ssm.port.secondary;

import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.model.Employee;

import java.util.Collection;
import java.util.Optional;

/**
 * Created at 3/10/2024 by Darius
 **/
public interface EmployeeRepository {

    Optional<Employee> findByCnp(final String cnp);

    Collection<Contract> saveAll(final Collection<Contract> contracts);

    Contract save(final Contract employeeContract);

}
