package ro.fisa.ssm.port.primary;

import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.structures.DoubleAppCollection;

/**
 * Created at 3/14/2024 by Darius
 **/
public interface DocumentService {

    DoubleAppCollection<Employee, Employee> saveEmployeesFromRegistry(AppDocument document);
}
