package ro.fisa.ssm.port.secondary;

import ro.fisa.ssm.model.Contract;
import ro.fisa.ssm.model.Employee;

import java.util.Collection;

/**
 * Created at 3/18/2024 by Darius
 **/
public interface UserRepository {

    Collection<Employee> fetchEmployees();
}
