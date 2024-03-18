package ro.fisa.ssm.port.primary;

import ro.fisa.ssm.model.Employee;

import java.util.Collection;

/**
 * Created at 3/10/2024 by Darius
 **/
public interface UserService {

    Collection<Employee> getEmployees();
}
