package ro.fisa.ssm.port.secondary;

import ro.fisa.ssm.model.Contract;

import java.util.Optional;

/**
 * Created at 3/10/2024 by Darius
 **/
public interface EmployeeRepository {

    Contract save(final Contract employeeContract);

    Optional<Contract> findByEmployeeCnp(final String cnp);
}
