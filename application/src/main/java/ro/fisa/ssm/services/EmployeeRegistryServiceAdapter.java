package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.exceptions.AppRuntimeException;
import ro.fisa.ssm.model.*;
import ro.fisa.ssm.port.primary.EmployeeRegistryService;
import ro.fisa.ssm.port.secondary.ContractRepository;
import ro.fisa.ssm.port.secondary.EmployerRepository;
import ro.fisa.ssm.security.AppSecurityProperties;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
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
    private final AppSecurityProperties.UserProperties userProperties;
    private final EmployerRepository employerRepository;
    private final ContractRepository contractRepository;
    private final PasswordEncoder passwordEncoder;
    private final TaskExecutor taskExecutor;

    @Override
    @Transactional
    public Collection<Contract> saveEmployeesFromRegistry(final AppDocument document, final String induction) {
        log.info("saveEmployeesFromRegistry");
        try (Workbook wb = WorkbookFactory.create(document.getInputStream())) {
            final Sheet sheet = wb.getSheetAt(0);
            final Map<String, Contract> extractedContractsMap = new HashMap<>();
            final Map<String, Contract> extractedInactiveContractsMap = new HashMap<>();
            log.info("Extracting employer details");
            final Employer employer = this.manageEmployer(sheet, induction);
            final var rowIterator = sheet.rowIterator();
            log.info("Extracting contract details");
            final AtomicReference<Employer> employerRef = new AtomicReference<>(employer);
            final Collection<CompletableFuture<Void>> futures = new LinkedList<>();
            while (rowIterator.hasNext()) {
                final Row row = rowIterator.next();
                if (row.getRowNum() <= ROW_TO_START) {
                    continue;
                }
                if (isRowEmpty(row)) {
                    break;
                }
                final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    final Employee extractEmployee = this.extractEmployee(row);
                    extractEmployee.setPassword(this.passwordEncoder.encode(this.userProperties.getPassword()));
                    final Contract extractedContract = this.extractContract(row, extractEmployee, employerRef.get());
                    final String contractNumber = extractedContract.getNumber();
                    if (extractedContract.isNotInactive()) {
                        extractedContractsMap.put(contractNumber, extractedContract);
                    } else {
                        extractedInactiveContractsMap.put(contractNumber, extractedContract);
                    }
                }, this.taskExecutor);
                futures.add(future);
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            log.info("Successfully extracted {} active and {} inactive contracts", extractedInactiveContractsMap.size(), extractedContractsMap.size());
            final Collection<String> newContractNumbers = extractedContractsMap.keySet();
            log.info("Checking existing contracts");
            final Collection<Contract> existingContracts = this.contractRepository.fetchAllByNumber(newContractNumbers);
            if (!existingContracts.isEmpty()) {
                log.info("Found {} existing contracts", existingContracts.size());
                existingContracts.parallelStream().forEach(existingContract -> {
                    final Contract newContract = extractedContractsMap.get(existingContract.getNumber());
                    if (existingContract.compare(newContract)) {
                        final Contract mergedContract = this.updateContract.apply(existingContract, newContract);
                        extractedContractsMap.put(mergedContract.getNumber(), mergedContract);
                    }
                });
            }

            final Collection<Contract> contractsToSave = extractedContractsMap.values();
            return this.contractRepository.saveAll(contractsToSave);
        } catch (IOException e) {
            throw new AppRuntimeException(e);
        }
    }

    private Employer manageEmployer(final Sheet sheet, final String inductionValue) {
        Employer employer = this.extractEmployerDetails(sheet);
        final Optional<Employer> employerOptional = this.employerRepository.fetchByName(employer.getName());
        if (employerOptional.isPresent()) {
            log.info("found existing employer = {}", employer.getName());
            employer = employerOptional.get();
            final Induction existingInduction = employer.getInduction();
            if (existingInduction == null) {
                employer.setInduction(new Induction(inductionValue));
            } else {
                existingInduction.setValue(inductionValue);
            }
        } else {
            log.info("Saving new employer = {}", employer.getName());
            employer.setInduction(new Induction(inductionValue));
            employer = this.employerRepository.save(employer);
        }

        return employer;
    }

    private Contract extractContract(final Row row, final Employee employee, final Employer employer) {
        final Contract contract = new Contract();
        contract.setEmployee(employee);
        contract.setEmployer(employer);
        getCellStringValue(row, ACTIVE_CELL).ifPresentOrElse(contract::setStatus, contract::enableErrors);
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
            oldContract.setEmployer(newContract.getEmployer());
            oldContract.setFixedTerm(newContract.getFixedTerm());
            oldContract.setStatus(newContract.getStatus());
            oldContract.setBaseSalary(newContract.getBaseSalary());
        } else {
            newContract.setEmployee(oldContract.getEmployee());
            newContract.setEmployer(oldContract.getEmployer());
            return newContract;
        }

        return oldContract;
    };

    private final BiConsumer<Employee, Employee> updateEmployee = (oldEmp, newEmp) -> {
        oldEmp.setCnp(newEmp.getCnp());
        oldEmp.setFirstName(newEmp.getFirstName());
        oldEmp.setLastName(newEmp.getLastName());
        oldEmp.setAddress(newEmp.getAddress());
        oldEmp.setNationality(newEmp.getNationality());
        oldEmp.setHasErrors(newEmp.isHasErrors());
        oldEmp.setInductionAccepted(newEmp.isInductionAccepted());
    };

}
