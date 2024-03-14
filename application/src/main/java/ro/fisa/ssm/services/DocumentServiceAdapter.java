package ro.fisa.ssm.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ro.fisa.ssm.model.AppDocument;
import ro.fisa.ssm.model.Employee;
import ro.fisa.ssm.model.Fullname;
import ro.fisa.ssm.port.primary.DocumentService;
import ro.fisa.ssm.utils.EmployeeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static ro.fisa.ssm.utils.DocumentUtils.DocumentExtension.DOT_XLSX;

/**
 * Created at 3/14/2024 by Darius
 **/

@Service
@Slf4j
public class DocumentServiceAdapter implements DocumentService {
    @Override
    public List<Employee> manageEmployeeRegistry(AppDocument document) {
        try {

            final Workbook wb = this.getWorkbookFromDocument(document);
            return this.getEmployeesFromRegistry(wb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Employee> getEmployeesFromRegistry(final Workbook workbook) {
        final Sheet sheet = workbook.getSheetAt(0);
        final Iterator<Row> rowIterator = sheet.rowIterator();
        final List<Employee> employees = new LinkedList<>();
        while (rowIterator.hasNext()) {
            final Row row = rowIterator.next();

            if (row.getRowNum() <= 10) {
                continue;
            }

            final Employee employee = this.extractPersonFromRegistryRow(row);
            if (employee != null) {
                employees.add(employee);
            }
        }

        return employees;
    }

    private boolean isRowEmpty(final Row row) {
        if (row == null) {
            return true;
        }
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }

        return true;

    }

    private Employee extractPersonFromRegistryRow(final Row row) {
        try {

            if (row == null || this.isRowEmpty(row)) {
                return null;
            }
            final boolean activeStatus = this.getEmployeeActiveStatusFromDocument(row);
            if (!activeStatus) {
                return null;
            }
            final Employee employee = new Employee();
            employee.setActive(activeStatus);

            this.setEmployeeName(row, employee);
            this.setEmployeeCnp(row, employee);

            return employee;
        } catch (Exception e) {
            log.error("Error at row {}", row.getRowNum());
            throw new RuntimeException(e);
        }
    }

    private boolean getEmployeeActiveStatusFromDocument(final Row row) {
        final Cell activeStatusCell = row.getCell(12);
        boolean activeStatus = false;
        if (activeStatusCell != null && activeStatusCell.getCellType() != CellType.BLANK) {
            activeStatus = activeStatusCell.getStringCellValue().trim().equalsIgnoreCase("activ");
        }
        return activeStatus;
    }

    private void setEmployeeName(final Row row, final Employee employee) {
        final Cell nameCell = row.getCell(2);
        if (nameCell != null && nameCell.getCellType() != CellType.BLANK) {
            final Fullname fullName = new Fullname(nameCell.getStringCellValue());
            employee.setFirstName(fullName.getFirstName());
            employee.setLastName(fullName.getLastName());
        }
    }

    private void setEmployeeCnp(final Row row, final Employee employee) {
        final Cell cnpCell = row.getCell(4);
        if (cnpCell != null && cnpCell.getCellType() != CellType.BLANK) {
            final String cnp = cnpCell.getStringCellValue();
            if (EmployeeUtils.isCnpValid(cnp)) {
                employee.setCnp(cnp);
            } else {
                log.warn("Invalid cnp for employee = {} at row {}, cnp will be set to null!", employee.getFullName(), row.getRowNum());
            }
        }
    }


    private Workbook getWorkbookFromDocument(AppDocument document) throws IOException {
        final String lowercaseFileName = document.getOriginalFileName().toLowerCase();
        final InputStream inputStream = document.getInputStream();

        return lowercaseFileName.endsWith(DOT_XLSX) ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
    }
}
