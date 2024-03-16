package ro.fisa.ssm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.model.Fullname;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.*;

/**
 * Created at 3/16/2024 by Darius
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeRegistryUtils {

    private static final int NAME_CELL = 2;
    private static final int CNP_CELL = 4;
    private static final int ACTIVE_CELL = 12;


    public static List<Employee> manageEmployeeRegistry(AppDocument document) throws IOException {
        final Sheet sheet = getSheetFromDocument(document, 0);
        return getEmployeesFromRegistry(sheet);

    }

    public static List<Employee> getEmployeesFromRegistry(final Sheet sheet) {
        final List<Employee> employees = new LinkedList<>();

        forEachRow(sheet, 10, row -> {
            final Employee employee = extractPersonFromRegistryRow(row);
            if (employee != null) {
                employees.add(employee);
            }
        });

        return employees;
    }


    public static Employee extractPersonFromRegistryRow(final Row row) {
        try {

            if (isRowEmpty(row)) {
                return null;
            }
            final boolean activeStatus = getCellStringValue(row, ACTIVE_CELL)
                    .filter(value -> value.equalsIgnoreCase("activ"))
                    .isPresent();
            if (!activeStatus) {
                return null;
            }
            final Employee employee = new Employee();
            employee.setActive(activeStatus);

            setEmployeeName(row, employee);
            setEmployeeCnp(row, employee);

            return employee;
        } catch (Exception e) {
            log.error("Error at row {}", row.getRowNum());
            throw new RuntimeException(e);
        }
    }


    private static void setEmployeeName(final Row row, final Employee employee) {
        getCellStringValue(row, NAME_CELL)
                .map(Fullname::new)
                .ifPresent(fullName -> {
                    employee.setFirstName(fullName.getFirstName());
                    employee.setLastName(fullName.getLastName());
                });
    }

    private static void setEmployeeCnp(final Row row, final Employee employee) {
        getCellStringValue(row, CNP_CELL)
                .ifPresentOrElse(cnp -> {
                    if (!EmployeeUtils.isCnpValid(cnp)) {
                        log.warn("Invalid cnp for employee = {} at row {}, cnp will be set to null!", employee.getFullName(), row.getRowNum());
                        employee.setHasErrors(true);
                    }
                }, () -> {
                    log.warn("Null cnp for employee = {} at row {}, cnp will be set to null!", employee.getFullName(), row.getRowNum());
                    employee.setHasErrors(true);
                });
    }
}
