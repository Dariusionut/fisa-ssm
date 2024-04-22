package ro.fisa.ssm.port.primary;

import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.model.InductionDetail;
import ro.fisa.ssm.structures.DomainPage;

import java.util.Collection;

/**
 * Created at 3/10/2024 by Darius
 **/
public interface UserService {

    Collection<Employee> getEmployees();

    DomainPage<InductionDetail> fetchUnacceptedInductions(int number, int size);

    void acceptInduction(final Long contractId);
}
