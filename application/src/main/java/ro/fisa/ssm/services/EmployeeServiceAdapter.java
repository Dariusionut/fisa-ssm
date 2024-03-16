package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.port.primary.DocumentService;
import ro.fisa.ssm.port.secondary.EmployeeRepository;
import ro.fisa.ssm.structures.DoubleAppCollection;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.forEachRow;
import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.getSheetFromDocument;
import static ro.fisa.ssm.utils.AppRoles.*;
import static ro.fisa.ssm.utils.EmployeeRegistryUtils.extractPersonFromRegistryRow;

/**
 * Created at 3/14/2024 by Darius
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceAdapter implements DocumentService {
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public DoubleAppCollection<Employee, Employee> saveEmployeesFromRegistry(AppDocument document) {
        try {
            final Sheet sheet = getSheetFromDocument(document, 0);
            final LinkedList<Employee> employees = new LinkedList<>();
            forEachRow(sheet, 10, row -> {
                final Employee employee = extractPersonFromRegistryRow(row);
                if (employee != null) {
                    employee.setRole(ROLE_EMPLOYEE);
                    employees.add(this.employeeRepository.save(employee));
                }
            });

            return new DoubleAppCollection<>(
                    employees.stream().filter(emp -> !emp.isHasErrors()).toList(),
                    employees.stream().filter(Employee::isHasErrors).toList()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
