package ro.fisa.ssm.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.fisa.ssm.model.*;
import ro.fisa.ssm.port.primary.UserService;
import ro.fisa.ssm.port.secondary.ContractRepository;
import ro.fisa.ssm.port.secondary.UserRepository;
import ro.fisa.ssm.security.AppUserDetails;
import ro.fisa.ssm.structures.DomainPage;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created at 3/18/2024 by Darius
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceAdapter implements UserService {

    private final UserRepository userRepository;
    private final ContractRepository contractRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Employee> getEmployees() {
        return this.userRepository.fetchEmployees();
    }

    @Override
    @Transactional(readOnly = true)
    public DomainPage<InductionDetail> fetchUnacceptedInductions(int number, int size) {
        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        final AppUserDetails details = (AppUserDetails) authentication.getPrincipal();
        final long employeeId = details.getId();
        final Page<InductionDetail> detailPage = this.contractRepository
                .fetchByEmployeeId(employeeId, PageRequest.of(number, size))
                .map(contract -> {
                    final InductionDetail inductionDetail = new InductionDetail();
                    final EmployeeSimpleDetails employeeSimpleDetails = new EmployeeSimpleDetails();
                    inductionDetail.setEmployee(employeeSimpleDetails);
                    final Employer employer = contract.getEmployer();
                    final Employee employee = contract.getEmployee();
                    employeeSimpleDetails.setFirstName(employee.getFirstName());
                    employeeSimpleDetails.setLastName(employee.getLastName());
                    employeeSimpleDetails.setId(employee.getId());
                    employeeSimpleDetails.setCreatedAt(employee.getCreatedAt());
                    employeeSimpleDetails.setUpdatedAt(employee.getUpdatedAt());
                    final Induction induction = employer.getInduction();
                    inductionDetail.setLastUpdate(induction.getUpdatedAt());
                    inductionDetail.setEmployerName(employer.getName());
                    inductionDetail.setContractNo(contract.getNumber());
                    inductionDetail.setContractId(contract.getId());
                    final LocalDateTime lastValidate = contract.getInductionAcceptedAt();
                    final boolean isAccepted = lastValidate == null || lastValidate.isBefore(induction.getUpdatedAt());
                    inductionDetail.setAccepted(!isAccepted);
                    return inductionDetail;
                });
        return DomainPage.fromPage(detailPage);
    }

    @Override
    public void acceptInduction(Long contractId) {
        this.contractRepository.acceptInduction(contractId);
    }
}
