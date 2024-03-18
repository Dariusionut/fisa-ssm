package ro.fisa.ssm.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import ro.fisa.ssm.enums.ContractDuration;
import ro.fisa.ssm.model.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.*;

/**
 * Created at 3/16/2024 by Darius
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeRegistryUtils {
    public static final int ROW_TO_START = 10;
    private static final int ROW_EMPLOYEE_TABLE = 10;
    public static final int EMPLOYER_DETAILS_CELL = 3;

    private static final int NAME_CELL = 2;
    private static final int CNP_CELL = 4;
    private static final int NATIONALITY_CELL = 5;
    private static final int ADDRESS_CELL = 7;
    private static final int CONTRACT_NUMBER_CELL = 8;
    private static final int CONTRACT_DURATION_TYPE_CELL = 10;
    private static final int JOB_NAME_CELL = 11;
    private static final int ACTIVE_CELL = 12;
    private static final int SALARY_CELL = 13;


    public static Optional<Contract> extractEmployeeFromRegistryRow(final Row row) {
        try {
            if (isRowEmpty(row)) {
                return Optional.empty();
            }
            final boolean activeStatus = getCellStringValue(row, ACTIVE_CELL)
                    .filter(value -> value.equalsIgnoreCase("activ"))
                    .isPresent();
            if (!activeStatus) {
                return Optional.empty();
            }
            final Employee
                    employee = new Employee();
            final CompletableFuture<Contract> contractFuture = ContractHelper.generateAndSetContract(row, employee);
            final CompletableFuture<Void> nameFuture = setEmployeeName(row, employee);
            final CompletableFuture<Void> cnpFuture = setEmployeeCnp(row, employee);
            final CompletableFuture<Void> nationalityFuture = setNationality(row, employee);
            final CompletableFuture<Void> addressFuture = getAddress(row, employee);

            CompletableFuture.allOf(contractFuture, nameFuture, cnpFuture, nationalityFuture, addressFuture).join();
            final Contract contract = contractFuture.get();
            return Optional.of(contract);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error at row {}", row.getRowNum());
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private static CompletableFuture<Void> getAddress(final Row row, final Employee
            employee) {
        return CompletableFuture.supplyAsync(() -> {
            getCellStringValue(row, ADDRESS_CELL)
                    .map(String::trim)
                    .ifPresentOrElse(employee::setAddress, employee::enableErrors);
            return null;
        });
    }

    private static CompletableFuture<Void> setNationality(final Row row, final Employee
            employee) {
        return CompletableFuture.supplyAsync(() -> {
            getCellStringValue(row, NATIONALITY_CELL)
                    .map(String::trim)
                    .map(Nationality::new)
                    .ifPresentOrElse(employee::setNationality, employee::enableErrors);
            return null;
        });
    }

    private static CompletableFuture<Void> setEmployeeName(final Row row, final Employee
            employee) {
        return CompletableFuture.supplyAsync(() -> {
            getCellStringValue(row, NAME_CELL)
                    .map(FullName::new)
                    .ifPresentOrElse(fullName -> {
                        fullName.getFirstName()
                                .ifPresentOrElse(employee::setFirstName, employee::enableErrors);
                        fullName.getLastName()
                                .ifPresentOrElse(employee::setLastName, employee::enableErrors);
                    }, employee::enableErrors);
            return null;
        });
    }

    private static CompletableFuture<Void> setEmployeeCnp(final Row row, final Employee
            employee) {
        return CompletableFuture.supplyAsync(() -> {
            getCellStringValue(row, CNP_CELL)
                    .ifPresentOrElse(cnp -> {
                        employee.setCnp(cnp);
                        if (!EmployeeUtils.isCnpValid(cnp)) {
                            log.warn("Invalid cnp for employee = {} at row {}, cnp will be set to null!", employee.getFullName(), row.getRowNum());
                            employee.setHasErrors(true);
                        }
                    }, () -> {
                        log.warn("Null cnp for employee = {} at row {}, cnp will be set to null!", employee.getFullName(), row.getRowNum());
                        employee.setHasErrors(true);
                    });
            return null;
        });
    }

    @Slf4j
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ContractHelper {

        static CompletableFuture<Contract> generateAndSetContract(final Row row, Employee
                employee) {
            return CompletableFuture.supplyAsync(() -> {
                final Contract employeeContract = new Contract();
                employeeContract.setActiveStatus(true);
                getCellStringValue(row, CONTRACT_NUMBER_CELL)
                        .map(String::trim)
                        .ifPresentOrElse(employeeContract::setNumber, employee::enableErrors);

                getCellStringValue(row, CONTRACT_DURATION_TYPE_CELL)
                        .map(String::trim)
                        .ifPresentOrElse(type -> {
                            if (type.equalsIgnoreCase(ContractDuration.FIXED_TERM.value().toLowerCase())) {
                                employeeContract.setFixedTerm(true);
                            } else if (type.equalsIgnoreCase(ContractDuration.NON_FIXED_TERM.value().toLowerCase())) {
                                employeeContract.setFixedTerm(false);
                            } else {
                                employee.enableErrors();
                            }
                        }, employee::enableErrors);

                getCellStringValue(row, JOB_NAME_CELL)
                        .map(String::trim)
                        .map(Job::new)
                        .ifPresentOrElse(employeeContract::setJob, employee::enableErrors);

                getCellDoubleValue(row, SALARY_CELL)
                        .ifPresentOrElse(employeeContract::setBaseSalary, employee::enableErrors);
                employeeContract.setEmployee(employee);
                return employeeContract;
            });
        }
    }
}
