package ro.fisa.ssm.port.secondary;

import ro.fisa.ssm.model.Employee;

/**
 * Created at 3/10/2024 by Darius
 **/
public interface EmployeeRepository {

    Employee save(final Employee employee);
}
