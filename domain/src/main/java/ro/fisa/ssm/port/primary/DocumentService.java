package ro.fisa.ssm.port.primary;

import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Employee;

import java.util.List;

/**
 * Created at 3/14/2024 by Darius
 **/
public interface DocumentService {

    List<Employee> manageEmployeeRegistry(AppDocument document);
}
