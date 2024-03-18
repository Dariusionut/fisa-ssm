package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.*;
import ro.fisa.ssm.port.primary.EmployeeRegistryService;
import ro.fisa.ssm.port.secondary.EmployeeRepository;
import ro.fisa.ssm.port.secondary.JobRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.getCellStringValue;
import static ro.fisa.ssm.persistence.utils.documents.ExcelUtils.getSheetFromDocument;
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
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.user.password}")
    private String initialPassword = "initialPassword";

    @Override
    @Transactional
    public Collection<Contract> saveEmployeesFromRegistry(AppDocument document) {
        try {
            final Sheet sheet = getSheetFromDocument(document, 0);
            final LinkedList<Contract> employeeContracts = new LinkedList<>();

            this.extractContract(sheet, employeeContracts);
            log.info("Successfully managed {} employees", employeeContracts.size());
            return employeeContracts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void extractContract(final Sheet sheet, Collection<Contract> contracts) {
        final Iterator<Row> rowIterator = sheet.rowIterator();
        final Employer employer = this.extractEmployerDetails(sheet);
        while (rowIterator.hasNext()) {
            final Row row = rowIterator.next();
            if (row.getRowNum() <= ROW_TO_START) {
                continue;
            }
            extractEmployeeFromRegistryRow(row)
                    .map(newContract -> {
                        final Employee newEmployee = newContract.getEmployee();
                        Optional<Contract> dbContractOptional = this.employeeRepository.findByEmployeeCnp(newEmployee.getCnp());
                        return dbContractOptional.map(oldContract -> this.updateContract(oldContract, newContract)).orElseGet(() -> {
                            final Employee newEmp = newContract.getEmployee();
                            newEmp.setPassword(this.encodedInitialPassword());
                            newEmp.setNewAdded(true);
                            return newContract;
                        });
                    })
                    .map(contract -> {
                        contract.getEmployee().setRole(ROLE_EMPLOYEE);
                        contract.setEmployer(employer);
                        return this.employeeRepository.save(contract);
                    })
                    .ifPresent(contracts::add);

        }
    }

    private String encodedInitialPassword() {
        return this.passwordEncoder.encode(initialPassword);
    }

    private Contract updateContract(final Contract oldEmployeeContract, final Contract newEmployeeContract) {
        final Employee oldEmp = oldEmployeeContract.getEmployee();
        final Employee newEmp = newEmployeeContract.getEmployee();
        if (oldEmployeeContract.getNumber().equals(newEmployeeContract.getNumber())) {
            final Job newJob = newEmployeeContract.getJob();
            oldEmployeeContract.setFixedTerm(newEmployeeContract.getFixedTerm());
            oldEmployeeContract.setBaseSalary(newEmployeeContract.getBaseSalary());
            oldEmployeeContract.setActiveStatus(newEmployeeContract.isActiveStatus());
            oldEmployeeContract.setJob(newEmployeeContract.getJob());
            newEmployeeContract.getJob().setName(newEmployeeContract.getJob().getName());
            this.jobRepository.fetchByName(newJob.getName()).ifPresent(oldEmployeeContract::setJob);
        } else {
            this.employeeRepository.save(newEmployeeContract);
        }

        if (newEmp.getCnp().equals(oldEmp.getCnp())) {
            oldEmp.setFirstName(newEmp.getFirstName());
            oldEmp.setLastName(newEmp.getLastName());
            oldEmp.setAddress(newEmp.getAddress());
            oldEmp.setNationality(newEmp.getNationality());
            oldEmp.setActive(newEmp.isActive());
            oldEmp.setHasErrors(newEmp.isHasErrors());
            oldEmp.setInductionAccepted(newEmp.isInductionAccepted());
        }

        return oldEmployeeContract;
    }

}
