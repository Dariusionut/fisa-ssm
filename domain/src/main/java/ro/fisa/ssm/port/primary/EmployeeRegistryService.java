package ro.fisa.ssm.port.primary;

import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Contract;

import java.util.Collection;

/**
 * Created at 3/14/2024 by Darius
 **/
public interface EmployeeRegistryService {

    Collection<Contract> saveEmployeesFromRegistry(AppDocument document);
}
