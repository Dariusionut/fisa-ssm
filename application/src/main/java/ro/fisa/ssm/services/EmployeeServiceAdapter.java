package ro.fisa.ssm.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.port.primary.DocumentService;
import ro.fisa.ssm.structures.DoubleAppCollection;

import java.io.IOException;
import java.util.LinkedList;

import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.forEachRow;
import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.getSheetFromDocument;
import static ro.fisa.ssm.utils.EmployeeRegistryUtils.extractPersonFromRegistryRow;

/**
 * Created at 3/14/2024 by Darius
 **/

@Service
@Slf4j
public class EmployeeServiceAdapter implements DocumentService {
    @Override
    public DoubleAppCollection<Employee, Employee> saveEmployeesFromRegistry(AppDocument document) {
        try {
            final Sheet sheet = getSheetFromDocument(document, 0);
            final LinkedList<Employee> noErrEmployees = new LinkedList<>();
            final LinkedList<Employee> errEmployees = new LinkedList<>();
            forEachRow(sheet, 10, row -> {
                final Employee employee = extractPersonFromRegistryRow(row);
                if (employee != null) {
                    if (employee.isHasErrors()) {
                        errEmployees.add(employee);
                    } else {
                        noErrEmployees.add(employee);
                    }
                }
            });

            return new DoubleAppCollection<>(noErrEmployees, errEmployees);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
