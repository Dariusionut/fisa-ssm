package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.exceptions.AppRuntimeException;
import ro.fisa.ssm.model.*;
import ro.fisa.ssm.port.primary.EmployeeRegistryService;
import ro.fisa.ssm.port.secondary.ContractRepository;
import ro.fisa.ssm.port.secondary.EmployeeRepository;
import ro.fisa.ssm.port.secondary.JobRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.*;
import static ro.fisa.ssm.utils.AppRoles.ROLE_EMPLOYEE;
import static ro.fisa.ssm.utils.EmployeeRegistryUtils.*;

/**
 * Created at 3/14/2024 by Darius
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeRegistryServiceAdapter implements EmployeeRegistryService {
    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.user.password}")
    private String initialPassword = "initialPassword";


    @Override
    @Transactional
    public Collection<Contract> saveEmployeesFromRegistry(AppDocument document) {
        try (Workbook wb = WorkbookFactory.create(document.getInputStream())) {
            final Sheet sheet = wb.getSheetAt(0);
            final List<Contract> contracts = new LinkedList<>();
            final Employer employer = this.extractEmployerDetails(sheet);
            final var rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                final Row row = rowIterator.next();
                if (row.getRowNum() <= ROW_TO_START) {
                    continue;
                }
                if (isRowEmpty(row)) {
                    break;
                }
                final var employee = this.extractEmployee(row);
                final var extractedContract = this.extractContract(row, employee, employer);
                final var contractOptional = this.contractRepository.fetchByNumber(extractedContract.getNumber());

                final var mergedContract = contractOptional
                        .map(oldContract -> this.updateContract.apply(oldContract, extractedContract))
                        .orElseGet(() -> {
                            employee.setPassword(this.passwordEncoder.encode(this.initialPassword));
                            employee.setRole(ROLE_EMPLOYEE);
                            employee.setNewAdded(true);

                            return extractedContract;
                        });
                contracts.add(mergedContract);

            }
            log.info("Successfully managed {} contracts", contracts.size());
            return this.employeeRepository.saveAll(contracts);
        } catch (IOException e) {
            throw new AppRuntimeException(e);
        }
    }

    private Contract extractContract(final Row row, final Employee employee, final Employer employer) {
        final Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setEmployer(employer);
        getCellStringValue(row, ACTIVE_CELL).ifPresentOrElse(contract::setActiveStatus, contract::enableErrors);
        getCellStringValue(row, CONTRACT_NUMBER_CELL).ifPresentOrElse(contract::setNumber, contract::enableErrors);
        getCellStringValue(row, CONTRACT_DURATION_TYPE_CELL).ifPresentOrElse(contract::setFixedTerm, contract::enableErrors);
        getCellStringValue(row, JOB_NAME_CELL).ifPresentOrElse(contract::setJob, contract::enableErrors);
        getCellDoubleValue(row, SALARY_CELL).ifPresentOrElse(contract::setBaseSalary, contract::enableErrors);
        return contract;
    }

    private Employee extractEmployee(final Row row) {
        final Employee employee = new Employee();
        getCellStringValue(row, CNP_CELL).ifPresentOrElse(employee::setCnp, () -> {
            log.warn("Null cnp for employee = {} at row {}, cnp will be set to null!", employee.getFullName(), row.getRowNum());
            employee.setHasErrors(true);
        });
        employee.setRole(ROLE_EMPLOYEE);
        getCellStringValue(row, NAME_CELL).ifPresentOrElse(employee::setName, employee::enableErrors);
        getCellStringValue(row, NATIONALITY_CELL).map(Nationality::new).ifPresentOrElse(employee::setNationality, employee::enableErrors);
        getCellStringValue(row, ADDRESS_CELL).ifPresentOrElse(employee::setAddress, employee::enableErrors);
        return employee;
    }

    private Employer extractEmployerDetails(final Sheet sheet) {
        final Employer employer = new Employer();

        getCellStringValue(sheet.getRow(6).getCell(EMPLOYER_DETAILS_CELL))
                .ifPresent(employer::setName);
        getCellStringValue(sheet.getRow(7).getCell(EMPLOYER_DETAILS_CELL))
                .ifPresent(employer::setCuiCif);
        getCellStringValue(sheet.getRow(8).getCell(EMPLOYER_DETAILS_CELL))
                .ifPresent(employer::setCaen);

        return employer;
    }


    private final BinaryOperator<Contract> updateContract = (oldContract, newContract) -> {
        this.updateEmployee.accept(oldContract.getEmployee(), newContract.getEmployee());
        final Job job = newContract.getJob();

        if (oldContract.compare(newContract)) {
            oldContract.setJob(job);
            oldContract.setFixedTerm(newContract.getFixedTerm());
            oldContract.setActiveStatus(newContract.isActiveStatus());
            oldContract.setBaseSalary(newContract.getBaseSalary());
        } else {
            newContract.setEmployee(oldContract.getEmployee());
            newContract.setEmployer(oldContract.getEmployer());
            return newContract;
        }

        return oldContract;
    };

    private final BiConsumer<Employee, Employee> updateEmployee = (oldEmp, newEmp) -> {
        oldEmp.setFirstName(newEmp.getFirstName());
        oldEmp.setLastName(newEmp.getLastName());
        oldEmp.setAddress(newEmp.getAddress());
        oldEmp.setNationality(newEmp.getNationality());
        oldEmp.setActive(newEmp.isActive());
        oldEmp.setHasErrors(newEmp.isHasErrors());
        oldEmp.setInductionAccepted(newEmp.isInductionAccepted());
    };

}
